package ru.systemairac.calculator.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.service.allinterface.HumidifierService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class HumidifierServiceTest {

    @Autowired
    private HumidifierService service;

    @Autowired
    private HumidifierRepository repository;

    private static FakeGenerator fakeGenerator;
    private static Random random;

    @BeforeAll
    public static void init() {
        fakeGenerator = new FakeGenerator();
        random = new Random();
    }

    @BeforeEach
    void cleanTable() {
        repository.deleteAll();
    }







    @Test
    public void saveHumidifier() {
        service.save(fakeGenerator.fakeGoodHumidifier());
    }

    @Test
    public void saveHumidifiersWithSameArticleNumber() {
        Humidifier[] humidifiers = {fakeGenerator.fakeGoodHumidifier(), fakeGenerator.fakeGoodHumidifier()};
        humidifiers[0].setArticleNumber(humidifiers[1].getArticleNumber());
        service.save(humidifiers[0]);
        assertThrows(DataIntegrityViolationException.class,
                () -> service.save(humidifiers[1])
        );
    }

    @Test
    public void saveManyHumidifiers() {
        int NUMBER = 100;
        List<Humidifier> humidifiers = fakeGenerator.fakeListOfGoodHumidifiers(NUMBER);
        service.saveAll(humidifiers);
    }

    @Test
    public void saveManyHumidifiersWithSameArticleNumber() {
        int NUMBER = 4;
        List<Humidifier> list = fakeGenerator.fakeListOfGoodHumidifiers(NUMBER);
        for (Humidifier humidifier : list)
            humidifier.setArticleNumber(list.get(0).getArticleNumber());
        assertThrows(DataIntegrityViolationException.class,
                () -> service.saveAll(list)
        );
    }

    @Test
    public void findSuitableHumidifiersFixed1() {
        int NUMBER = 25;
        List<Humidifier> list = fakeGenerator.fakeListOfGoodHumidifiers(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            Humidifier humidifier = list.get(i);
            humidifier.setCapacity((i + 1) * 3d);

            if (i % 2 == 0) // 0, 2, 4, ...
                humidifier.setVoltage(EnumVoltageType.ONE_PHASE_220V);
            else // 1, 3, 5, ...
                humidifier.setVoltage(EnumVoltageType.THREE_PHASE_380V);

            if (i % 3 == 0) // 0, 3, 6, 9, ...
                humidifier.setHumidifierType(EnumHumidifierType.ELECTRODE);
            else // 1, 2, 4, 5, 7, 8, ...
                humidifier.setHumidifierType(EnumHumidifierType.HEATING_ELEMENT);
        }
        service.saveAll(list);

        // ищем для производительности = 15, фазности = 3, электродный
        final double CAPACITY = 15;
        final EnumVoltageType PHASE = EnumVoltageType.THREE_PHASE_380V;
        final EnumHumidifierType TYPE = EnumHumidifierType.ELECTRODE;

        checkHumidifiersFromService(list, CAPACITY, PHASE, TYPE);
    }

    @Test
    public void findSuitableHumidifiersFixed2() {
        int NUMBER = 25;
        List<Humidifier> list = fakeGenerator.fakeListOfGoodHumidifiers(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            Humidifier humidifier = list.get(i);
            humidifier.setCapacity((double) (i % 2 == 0 ? i : i * 3));

            if (i % 3 == 0) // 0, 3, 6, 9, ...
                humidifier.setVoltage(EnumVoltageType.THREE_PHASE_380V);
            else // 1, 2, 4, 5, 7, 8, ...
                humidifier.setVoltage(EnumVoltageType.ONE_PHASE_220V);

            if (i % 3 == 0)
                humidifier.setHumidifierType(EnumHumidifierType.ELECTRODE);
            else
                humidifier.setHumidifierType(EnumHumidifierType.HEATING_ELEMENT);
        }
        service.saveAll(list);

        // ищем для производительности = 1, фазности = 3, электродный
        final double CAPACITY = 1;
        final EnumVoltageType PHASE = EnumVoltageType.ONE_PHASE_220V;
        final EnumHumidifierType TYPE = EnumHumidifierType.HEATING_ELEMENT;

        checkHumidifiersFromService(list, CAPACITY, PHASE, TYPE);
    }

    private void checkHumidifiersFromService(List<Humidifier> list, double CAPACITY, EnumVoltageType PHASE, EnumHumidifierType TYPE) {
        Humidifier[] expected = getSuitableHumidifiers(list, CAPACITY, PHASE, TYPE);
        Humidifier[] actual =
                service.findHumidifiers(CAPACITY, PHASE, TYPE)
                        .toArray(new Humidifier[0]);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            checkHumidifierFieldsEqual(expected[i], actual[i]);
        }
    }

    @Test
    public void findSuitableHumidifiersFixed3() {
        int NUMBER = 3;
        Humidifier[] arr = fakeGenerator.fakeListOfGoodHumidifiers(NUMBER).toArray(Humidifier[]::new);
        for (int i = 0; i < NUMBER; i++) {
            arr[i].setVoltage(EnumVoltageType.THREE_PHASE_380V);
            arr[i].setHumidifierType(EnumHumidifierType.ELECTRODE);
        }
        final double CAPACITY = 123;
        arr[0].setCapacity(CAPACITY);
        arr[1].setCapacity(CAPACITY + 0.001);
        arr[2].setCapacity(CAPACITY - 0.001);
        service.saveAll(Arrays.asList(arr));

        // ищем для производительности = 123, фазности = 3, электродный
        final EnumVoltageType PHASE = EnumVoltageType.THREE_PHASE_380V;
        final EnumHumidifierType TYPE = EnumHumidifierType.ELECTRODE;

        checkHumidifiersFromService(Arrays.asList(arr), CAPACITY, PHASE, TYPE);
    }

    @Test
    public void findSuitableHumidifiersFixed4() {
        int NUMBER = 4;
        Humidifier[] arr = fakeGenerator.fakeListOfGoodHumidifiers(NUMBER).toArray(Humidifier[]::new);
        for (int i = 0; i < NUMBER; i++) {
            final EnumVoltageType PHASE = EnumVoltageType.THREE_PHASE_380V;
            arr[i].setHumidifierType(EnumHumidifierType.ELECTRODE);
            arr[i].setVoltage(PHASE);
        }
        final double CAPACITY = 123;
        arr[0].setCapacity(CAPACITY);
        arr[1].setCapacity(CAPACITY + 0.001);
        arr[2].setCapacity(CAPACITY - 0.001);
        arr[3].setCapacity(CAPACITY + 1);
        service.saveAll(Arrays.asList(arr));

        // ищем для производительности = 123, фазности = 3, электродный
        final EnumVoltageType PHASE = EnumVoltageType.THREE_PHASE_380V;
        final EnumHumidifierType TYPE = EnumHumidifierType.ELECTRODE;

        List<Humidifier> actual = service.findHumidifiers(CAPACITY, PHASE, TYPE);
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
        final EnumVoltageType PHASE = EnumVoltageType.ONE_PHASE_220V;
        final EnumHumidifierType TYPE = EnumHumidifierType.HEATING_ELEMENT;

        checkHumidifiersFromService(list, CAPACITY, PHASE, TYPE);
    }

    @RepeatedTest(100)
    public void findSuitableHumidifiersRandom() {
        final int MAX_NUMBER_OF_ELEMENTS = 250;
        List<Humidifier> list = fakeGenerator.fakeListOfGoodHumidifiers(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        removeDuplicateCapacityOrArticle(list);
        service.saveAll(list);

        final double CAPACITY = random.nextDouble() * 120;
        final EnumVoltageType PHASE = EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)];
        final EnumHumidifierType TYPE = EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)];

        list.forEach(System.out::println);

        Humidifier[] expected = list.stream()
                .filter(h -> h.getCapacity() >= CAPACITY
                        && h.getVoltage() == PHASE
                        && h.getHumidifierType() == TYPE

                ).sorted(Comparator.comparingDouble(Humidifier::getCapacity))
                .limit(3)
                .toArray(Humidifier[]::new);
        Humidifier[] actual =
                service.findHumidifiers(CAPACITY, PHASE, TYPE)
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

    private Humidifier[] getSuitableHumidifiers(List<Humidifier> list, double capacity, EnumVoltageType voltageType, EnumHumidifierType type) {
        return list.stream()
                .filter(h -> h.getCapacity() >= capacity
                        && h.getVoltage() == voltageType
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
        assertEquals(expected.getVoltage(), actual.getVoltage());
        assertEquals(expected.getVoltage(), actual.getVoltage());
        assertEquals(expected.getNumberOfCylinders(), actual.getNumberOfCylinders());
        assertEquals(expected.getVaporPipeDiameter(), actual.getVaporPipeDiameter());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getHumidifierType(), actual.getHumidifierType());
    }

}
