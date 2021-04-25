package ru.systemairac.calculator.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.exception.HumidifierNotFoundException;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.service.allinterface.HumidifierService;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
        service.save(fakeGenerator.fakeHumidifier());
    }

    @Test
    public void saveHumidifiersWithSameArticleNumber() {
        Humidifier[] humidifiers = {fakeGenerator.fakeHumidifier(), fakeGenerator.fakeHumidifier()};
        humidifiers[0].setArticleNumber(humidifiers[1].getArticleNumber());
        service.save(humidifiers[0]);
        assertThrows(DataIntegrityViolationException.class,
                () -> service.save(humidifiers[1])
        );
    }

    @Test
    public void saveManyHumidifiers() {
        int NUMBER = 100;
        List<Humidifier> humidifiers = fakeGenerator.fakeHumidifierList(NUMBER);
        service.saveAll(humidifiers);
    }

    @Test
    public void saveManyHumidifiersWithSameArticleNumber() {
        int NUMBER = 4;
        List<Humidifier> list = fakeGenerator.fakeHumidifierList(NUMBER);
        for (Humidifier humidifier : list)
            humidifier.setArticleNumber(list.get(0).getArticleNumber());
        assertThrows(DataIntegrityViolationException.class,
                () -> service.saveAll(list)
        );
    }

    @Test
    public void findSuitableHumidifiersFixed1() {
        int NUMBER = 25;
        List<Humidifier> list = fakeGenerator.fakeHumidifierList(NUMBER);
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
        List<Humidifier> list = fakeGenerator.fakeHumidifierList(NUMBER);
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
            assertHumidifierFieldsEqual(expected[i], actual[i]);
        }
    }

    @Test
    public void findSuitableHumidifiersFixed3() {
        int NUMBER = 3;
        Humidifier[] arr = fakeGenerator.fakeHumidifierList(NUMBER).toArray(Humidifier[]::new);
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
        Humidifier[] arr = fakeGenerator.fakeHumidifierList(NUMBER).toArray(Humidifier[]::new);
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

    @RepeatedTest(75)
    public void findSuitableHumidifiersRandom() {
        final int MAX_NUMBER_OF_ELEMENTS = 250;
        List<Humidifier> list = fakeGenerator.fakeHumidifierList(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        list.forEach(h -> h.setId(null));
        removeDuplicateCapacityOrArticle(list);
        service.saveAll(list);

        final double CAPACITY = random.nextDouble() * 120;
        final EnumVoltageType PHASE = EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)];
        final EnumHumidifierType TYPE = EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)];

//        list.forEach(System.out::println);

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
            assertHumidifierFieldsEqual(expected[i], actual[i]);
        }
    }

    @RepeatedTest(25)
    public void findDtoHumidifiersRandom() {
        final int MAX_NUMBER_OF_ELEMENTS = 250;
        List<Humidifier> list = fakeGenerator.fakeHumidifierList(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        list.forEach(h -> h.setId(null));
        removeDuplicateCapacityOrArticle(list);
        service.saveAll(list);

        final double CAPACITY = random.nextDouble() * 120;
        final EnumVoltageType PHASE = EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)];
        final EnumHumidifierType TYPE = EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)];

//        list.forEach(System.out::println);

        Humidifier[] expected = list.stream()
                .filter(h -> h.getCapacity() >= CAPACITY
                        && h.getVoltage() == PHASE
                        && h.getHumidifierType() == TYPE

                ).sorted(Comparator.comparingDouble(Humidifier::getCapacity))
                .limit(3)
                .toArray(Humidifier[]::new);
        HumidifierDto[] actual =
                service.findDtoHumidifiers(CAPACITY, PHASE, TYPE)
                        .toArray(new HumidifierDto[0]);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertDtoFieldsEqual(expected[i], actual[i]);
        }
    }

    @Test
    public void findHumidifierByIdFail() {
        assertThrows(HumidifierNotFoundException.class, () ->
            service.findHumidifierById(random.nextLong())
        );
    }

    @Test
    public void getAllDiametersFixed() {
        final int NUMBER_OF_ELEMENTS = 5;
        List<Humidifier> humidifierList = fakeGenerator.fakeHumidifierList(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            humidifierList.get(i).setId((long) (i + 1));
            humidifierList.get(i).setVaporPipeDiameter((i + 5) * (i + 4));
        }
        Map<Long, Integer> actual = service.getAllDiameters(humidifierList);
        Map<Long, Integer> expected = new HashMap<>();
        for (long i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            expected.put(i + 1, (int) ((i + 5) * (i + 4)));
        }
        assertEquals(expected.size(), actual.size());
        for (Long key : expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @RepeatedTest(5)
    public void getAllDiametersRandom() {
        final int NUMBER_OF_ELEMENTS = 50;
        List<Humidifier> humidifierList = fakeGenerator.fakeHumidifierList(NUMBER_OF_ELEMENTS);
        Map<Long, Integer> actual = service.getAllDiameters(humidifierList);
        Map<Long, Integer> expected = new HashMap<>();
        for (Humidifier h : humidifierList) {
            expected.put(h.getId(), h.getVaporPipeDiameter());
        }
        assertEquals(expected.size(), actual.size());
        for (Long key : expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @RepeatedTest(3)
    public void findHumidifiersByIds() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<Humidifier> expectedList = fakeGenerator.fakeHumidifierList(NUMBER_OF_ELEMENTS);
        for (Humidifier h : expectedList) {
            h.setId(null);
            long id = repository.save(h).getId();
            h.setId(id);
        }
        List<Long> ids = expectedList.stream()
                .map(Humidifier::getId)
                .collect(Collectors.toList());
        List<Humidifier> actualList = service.findHumidifiersByIds(ids);
        actualList.forEach(h -> h.setVaporDistributors(null));
        actualList.forEach(h -> h.setHumidifierComponents(null));
        assertEquals(expectedList.size(), actualList.size());
        actualList.sort(Comparator.comparingLong(Humidifier::getId));
        expectedList.sort(Comparator.comparingLong(Humidifier::getId));
        assertArrayEquals(expectedList.toArray(new Humidifier[0]), actualList.toArray(new Humidifier[0]));
    }

    @RepeatedTest(3)
    public void findById() {
        Humidifier humidifier = fakeGenerator.fakeHumidifier();
        humidifier.setId(null);
        humidifier.setId(repository.save(humidifier).getId());
        HumidifierDto humidifierDto = service.findById(humidifier.getId());
        assertDtoFieldsEqual(humidifier, humidifierDto);
    }

    public static void assertDtoFieldsEqual(Humidifier entity, HumidifierDto dto) {
//        assertEquals(entity.getBrand(), dto.getBrand());
        // TODO: почему в HumidifierDto нет Brand?
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getImage(), dto.getImage());
        assertEquals(entity.getArticleNumber(), dto.getArticleNumber());
        assertEquals(entity.getElectricPower(), dto.getElectricPower());
        assertEquals(entity.getCapacity(), dto.getCapacity());
        assertEquals(entity.getVoltage(), dto.getVoltage());
        assertEquals(entity.getNumberOfCylinders(), dto.getNumberOfCylinders());
        assertEquals(entity.getVaporPipeDiameter(), dto.getVaporPipeDiameter());
        assertEquals(entity.getPrice(), dto.getPrice());
        assertEquals(entity.getHumidifierType(), dto.getHumidifierType());
    }

    public static void removeDuplicateCapacityOrArticle(List<Humidifier> list) {
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

    private void assertHumidifierFieldsEqual(Humidifier expected, Humidifier actual) {
        assertEquals(expected.getBrand(), actual.getBrand());
        assertEquals(expected.getArticleNumber(), actual.getArticleNumber());
        assertEquals(expected.getElectricPower(), actual.getElectricPower());
        assertEquals(expected.getCapacity(), actual.getCapacity());
        assertEquals(expected.getVoltage(), actual.getVoltage());
        assertEquals(expected.getNumberOfCylinders(), actual.getNumberOfCylinders());
        assertEquals(expected.getVaporPipeDiameter(), actual.getVaporPipeDiameter());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getHumidifierType(), actual.getHumidifierType());
    }

}
