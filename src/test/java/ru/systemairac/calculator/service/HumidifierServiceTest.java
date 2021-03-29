package ru.systemairac.calculator.service;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TableName;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HumidifierServiceTest {

    @Autowired
    private HumidifierService service;

    @Autowired
    private HumidifierRepository repository;

    private static Faker faker;
    private static Random random;
    private static Integer[] possibleVoltages;

    private static EnumHumidifierType type1 = EnumHumidifierType.ELECTRODE;
    private static EnumHumidifierType type2 = EnumHumidifierType.HEATING_ELEMENT;

    @BeforeAll
    public static void init() {
        faker = new Faker(new Locale("ru"), new RandomService());
        random = new Random();
        possibleVoltages = new Integer[]{220, 380};
    }

    @BeforeEach
    void cleanTable() {
        repository.deleteAll();
        repository.flush();
    }

    private Humidifier fakeGoodHumidifier() {
        BigDecimal price = BigDecimal.valueOf(random.nextInt(100_000_000) * 0.01);
        price = price.setScale(2, RoundingMode.FLOOR); // TODO: а как это происходит в БД?
        return Humidifier.builder()
                .id(null)
                .type(TableName.HUMIDIFIER)
                .articleNumber(faker.bothify("???###")) // должен быть Unique, вообще-то
                .brand(null) // TODO: пока без бренда
                .humidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .electricPower(random.nextDouble() * 90) // от 0 до 90, не зависит от capacity
                .capacity(random.nextDouble() * 120) // от 0 до 120
                .phase(random.nextInt(2) * 2 + 1) // 1 или 3
                .voltage(possibleVoltages[random.nextInt(possibleVoltages.length)]) // из списка
                .numberOfCylinders(1 + random.nextInt(3)) // от 1 до 3
                .vaporPipeDiameter(random.nextInt(31) + 15) // от 15 до 45
                .vaporDistributors(null) // TODO: пока без парораспределителей
                .humidifierComponents(null) // TODO: пока без компонентов
                .price(price) // от 0 до 1_000_000
                .build();
    }

    private List<Humidifier> fakeListOfGoodHumidifiers(int number) {
        List<Humidifier> list = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            list.add(fakeGoodHumidifier());
            Humidifier humidifier = list.get(i);
            humidifier.setArticleNumber(i + "_" + humidifier.getArticleNumber()); // нужно, так как у нас
        }
        return list;
    }

    private HumidifierDto fakeGoodHumidifierDto() {
        return HumidifierDto.builder()
                .id(null)
                .articleNumber(faker.bothify("???###")) // должен быть Unique, вообще-то
                .brand(null) // TODO: пока без бренда
                .humidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .electricPower(random.nextDouble() * 90) // от 0 до 90, не зависит от capacity
                .capacity(random.nextDouble() * 120) // от 0 до 120
                .phase(random.nextInt(2) * 2 + 1) // 1 или 3
                .voltage(possibleVoltages[random.nextInt(possibleVoltages.length)]) // из списка
                .numberOfCylinders(1 + random.nextInt(3)) // от 1 до 3
                .vaporPipeDiameter(random.nextInt(31) + 15) // от 15 до 45
                .price(BigDecimal.valueOf(random.nextInt(100_000_000) * 0.01)) // от 0 до 1_000_000
                .build();
    }

    @Test
    public void saveHumidifier() {
        service.save(fakeGoodHumidifier());
    }

    @Test
    public void saveHumidifiersWithSameArticleNumber() {
        Humidifier[] humidifiers = {fakeGoodHumidifier(), fakeGoodHumidifier()};
        humidifiers[0].setArticleNumber(humidifiers[1].getArticleNumber());
        service.save(humidifiers[0]);
        assertThrows(DataIntegrityViolationException.class,
                () -> service.save(humidifiers[1])
        );
    }

    @Test
    public void saveManyHumidifiers() {
        int NUMBER = 100;
        List<Humidifier> humidifiers = fakeListOfGoodHumidifiers(NUMBER);
        service.saveAll(humidifiers);
    }

    @Test
    public void saveManyHumidifiersWithSameArticleNumber() {
        int NUMBER = 4;
        List<Humidifier> list = fakeListOfGoodHumidifiers(NUMBER);
        for (Humidifier humidifier : list)
            humidifier.setArticleNumber(list.get(0).getArticleNumber());
        assertThrows(DataIntegrityViolationException.class,
                () -> service.saveAll(list)
        );
    }

    @Test
    public void findSuitableHumidifiersFixed1() {
        int NUMBER = 25;
        List<Humidifier> list = fakeListOfGoodHumidifiers(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            Humidifier humidifier = list.get(i);
            humidifier.setCapacity((i + 1) * 3d);

            if (i % 2 == 0) // 0, 2, 4, ...
                humidifier.setPhase(1);
            else // 1, 3, 5, ...
                humidifier.setPhase(3);

            if (i % 3 == 0) // 0, 3, 6, 9, ...
                humidifier.setHumidifierType(EnumHumidifierType.ELECTRODE);
            else // 1, 2, 4, 5, 7, 8, ...
                humidifier.setHumidifierType(EnumHumidifierType.HEATING_ELEMENT);
        }
        service.saveAll(list);

        // ищем для производительности = 15, фазности = 3, электродный
        final double CAPACITY = 15;
        final int PHASE = 3;
        final EnumHumidifierType TYPE = EnumHumidifierType.ELECTRODE;

        checkHumidifiersFromService(list, CAPACITY, PHASE, TYPE);
    }

    @Test
    public void findSuitableHumidifiersFixed2() {
        int NUMBER = 25;
        List<Humidifier> list = fakeListOfGoodHumidifiers(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            Humidifier humidifier = list.get(i);
            humidifier.setCapacity((double) (i % 2 == 0 ? i : i * 3));

            if (i % 3 == 0) // 0, 3, 6, 9, ...
                humidifier.setPhase(3);
            else // 1, 2, 4, 5, 7, 8, ...
                humidifier.setPhase(1);

            if (i % 3 == 0)
                humidifier.setHumidifierType(EnumHumidifierType.ELECTRODE);
            else
                humidifier.setHumidifierType(EnumHumidifierType.HEATING_ELEMENT);
        }
        service.saveAll(list);

        // ищем для производительности = 1, фазности = 3, электродный
        final double CAPACITY = 1;
        final int PHASE = 1;
        final EnumHumidifierType TYPE = EnumHumidifierType.HEATING_ELEMENT;

        checkHumidifiersFromService(list, CAPACITY, PHASE, TYPE);
    }

    private void checkHumidifiersFromService(List<Humidifier> list, double CAPACITY, int PHASE, EnumHumidifierType TYPE) {
        Humidifier[] expected = getSuitableHumidifiers(list, CAPACITY, PHASE, TYPE);
        Humidifier[] actual =
                service.findHumidifiers(CAPACITY, TYPE, PHASE)
                        .toArray(new Humidifier[0]);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            checkHumidifierFieldsEqual(expected[i], actual[i]);
        }
    }

    @Test
    public void findSuitableHumidifiersFixed3() {
        int NUMBER = 3;
        Humidifier[] arr = fakeListOfGoodHumidifiers(NUMBER).toArray(Humidifier[]::new);
        for (int i = 0; i < NUMBER; i++) {
            arr[i].setPhase(3);
            arr[i].setHumidifierType(EnumHumidifierType.ELECTRODE);
        }
        final double CAPACITY = 123;
        arr[0].setCapacity(CAPACITY);
        arr[1].setCapacity(CAPACITY + 0.001);
        arr[2].setCapacity(CAPACITY - 0.001);
        service.saveAll(Arrays.asList(arr));

        // ищем для производительности = 123, фазности = 3, электродный
        final int PHASE = 3;
        final EnumHumidifierType TYPE = EnumHumidifierType.ELECTRODE;

        checkHumidifiersFromService(Arrays.asList(arr), CAPACITY, PHASE, TYPE);
    }

    @Test
    public void findSuitableHumidifiersFixed4() {
        int NUMBER = 4;
        Humidifier[] arr = fakeListOfGoodHumidifiers(NUMBER).toArray(Humidifier[]::new);
        for (int i = 0; i < NUMBER; i++) {
            arr[i].setPhase(3);
            arr[i].setHumidifierType(EnumHumidifierType.ELECTRODE);
        }
        final double CAPACITY = 123;
        arr[0].setCapacity(CAPACITY);
        arr[1].setCapacity(CAPACITY + 0.001);
        arr[2].setCapacity(CAPACITY - 0.001);
        arr[3].setCapacity(CAPACITY + 1);
        service.saveAll(Arrays.asList(arr));

        // ищем для производительности = 123, фазности = 3, электродный
        final int PHASE = 3;
        final EnumHumidifierType TYPE = EnumHumidifierType.ELECTRODE;

        List<Humidifier> actual = service.findHumidifiers(CAPACITY, TYPE, PHASE);
        assertEquals(arr[0].getArticleNumber(), actual.get(0).getArticleNumber());
        assertEquals(arr[1].getArticleNumber(), actual.get(1).getArticleNumber());
        assertEquals(arr[3].getArticleNumber(), actual.get(2).getArticleNumber());

    }

    @Test
    public void findSuitableHumidifiersFixedNoHumidifiers() {
        List<Humidifier> list = new ArrayList<>();
        service.saveAll(list);

        // ищем для производительности = 50, фазности = 1, ТЭН
        final double CAPACITY = 50;
        final int PHASE = 1;
        final EnumHumidifierType TYPE = EnumHumidifierType.HEATING_ELEMENT;

        checkHumidifiersFromService(list, CAPACITY, PHASE, TYPE);
    }

    @RepeatedTest(250)
    public void findSuitableHumidifiersRandom() {
        final int MAX_NUMBER_OF_ELEMENTS = 250;
        List<Humidifier> list = fakeListOfGoodHumidifiers(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        removeDuplicateCapacityOrArticle(list);
        service.saveAll(list);

        final double CAPACITY = random.nextDouble() * 120;
        final int PHASE = random.nextInt(2) * 2 + 1;
        final EnumHumidifierType TYPE = EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)];

        list.forEach(System.out::println);

        Humidifier[] expected = list.stream()
                .filter(h -> h.getCapacity() >= CAPACITY
                        && h.getPhase() == PHASE
                        && h.getHumidifierType() == TYPE

                ).sorted(Comparator.comparingDouble(Humidifier::getCapacity))
                .limit(3)
                .toArray(Humidifier[]::new);
        Humidifier[] actual =
                service.findHumidifiers(CAPACITY, TYPE, PHASE)
                        .toArray(new Humidifier[0]);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            checkHumidifierFieldsEqual(expected[i], actual[i]);
        }
    }

    private void removeDuplicateCapacityOrArticle(List<Humidifier> list) {
        Set<Double> capacitySet = new HashSet<>();
        Set<String> articleSet = new HashSet<>();
        Iterator<Humidifier> it = list.iterator();
        while (it.hasNext()) {
            Humidifier h = it.next();
            if (capacitySet.contains(h.getCapacity()) || articleSet.contains(h.getArticleNumber())) {
                it.remove();
            } else {
                capacitySet.add(h.getCapacity());
                articleSet.add(h.getArticleNumber());
            }
        }
    }

    private Humidifier[] getSuitableHumidifiers(List<Humidifier> list, double capacity, int phase, EnumHumidifierType type) {
        return list.stream()
                .filter(h -> h.getCapacity() >= capacity
                        && h.getPhase() == phase
                        && h.getHumidifierType() == type
                ).sorted(Comparator.comparingDouble(Humidifier::getCapacity))
                .limit(3)
                .toArray(Humidifier[]::new);
    }

    private void checkHumidifierFieldsEqual(Humidifier expected, Humidifier actual) {
        assertEquals(expected.getBrand(), actual.getBrand());
        assertEquals(expected.getArticleNumber(), actual.getArticleNumber());
        assertEquals(expected.getElectricPower(), actual.getElectricPower());
        assertEquals(expected.getCapacity(), actual.getCapacity());
        assertEquals(expected.getPhase(), actual.getPhase());
        assertEquals(expected.getVoltage(), actual.getVoltage());
        assertEquals(expected.getNumberOfCylinders(), actual.getNumberOfCylinders());
        assertEquals(expected.getVaporPipeDiameter(), actual.getVaporPipeDiameter());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getHumidifierType(), actual.getHumidifierType());
    }

}
