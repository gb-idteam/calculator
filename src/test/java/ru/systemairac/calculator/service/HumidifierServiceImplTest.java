package ru.systemairac.calculator.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierType;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TableName;
import ru.systemairac.calculator.repository.HumidifierRepository;
import ru.systemairac.calculator.repository.HumidifierTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HumidifierServiceImplTest {
    @Autowired
    private HumidifierService humidifierService;
    @Autowired
    private HumidifierRepository humidifierRepository;
    @Autowired
    private HumidifierTypeRepository humidifierTypeRepository;
    private List<Humidifier> humidifiers = new ArrayList<>();
    private static Humidifier humidifier1 = new Humidifier();
    private static Humidifier humidifier2 = new Humidifier();
    private static Humidifier humidifier3 = new Humidifier();
    private static Humidifier humidifier4 = new Humidifier();
    private static Humidifier humidifier5 = new Humidifier();
    private static Humidifier humidifier6 = new Humidifier();
    private static Humidifier humidifier7 = new Humidifier();
    private EnumHumidifierType type1 = EnumHumidifierType.ELECTRODE;
    private EnumHumidifierType type2 = EnumHumidifierType.HEATING_ELEMENT;

    private static Humidifier generateHumidifier(EnumHumidifierType type, double capacity, int phase) {
        Humidifier humidifier =Humidifier.builder().
                humidifierType(type).
                type(TableName.HUMIDIFIER).
                capacity(capacity).
                phase(phase).
                build();
        return humidifier;
    }

    void creatEntities(){
        humidifier1 = generateHumidifier(type1,10,1);
        humidifier2 = generateHumidifier(type1,20,1);
        humidifier3 = generateHumidifier(type1,30,1);
        humidifier4 = generateHumidifier(type1,20,1);
        humidifier5 = generateHumidifier(type2,20,1);
        humidifier6 = generateHumidifier(type1,5,1);
        humidifier7 = generateHumidifier(type1,20,3);
    }

    static void creatEntitiesWithoutType(){
        humidifier1 = generateHumidifier(null,10,1);
        humidifier2 = generateHumidifier(null,20,1);
        humidifier3 = generateHumidifier(null,30,1);
        humidifier4 = generateHumidifier(null,20,3);
        humidifier5 = generateHumidifier(null,25,1);
        humidifier6 = generateHumidifier(null,5,1);
        humidifier7 = generateHumidifier(null,30,3);
    }

    // Проблема при добавлении типа увлажнителя
    //org.springframework.dao.InvalidDataAccessApiUsageException: detached entity passed to persist: ru.systemairac.calculator.domain.humidifier.HumidifierType; nested exception is org.hibernate.PersistentObjectException: detached entity passed to persist: ru.systemairac.calculator.domain.humidifier.HumidifierType
//    @Test
//    void findRightHumidifiers3count() {
//        creatEntities();
//        humidifierService.saveAll(Stream.of(humidifier1,humidifier2,humidifier3,humidifier4,humidifier5,humidifier6,humidifier7).collect(Collectors.toList()));
//        List<Humidifier> resultHumidifiers = humidifierRepository.findDistinctFirst3ByCapacityGreaterThanEqualAndHumidifierType_TypeLikeAndPhaseOrderByCapacity(3,EnumHumidifierType.ELECTRODE,1);
//        assertArrayEquals(Stream.of(humidifier1, humidifier2, humidifier3).toArray(),resultHumidifiers.toArray());
//    }

    @BeforeEach
    @AfterEach
    void cleanTable() {
        humidifierRepository.deleteAll();
    }

    @Test
    void findRightHumidifiersWithoutType1() {
        creatEntitiesWithoutType();
        humidifierService.saveAll(Stream.of(humidifier1,humidifier2,humidifier3,humidifier4,humidifier5,humidifier6,humidifier7).collect(Collectors.toList()));
        List<Humidifier> resultHumidifiers = humidifierRepository.findDistinctFirst3ByCapacityGreaterThanEqualAndPhaseOrderByCapacity(3,1);
        assertTrue(resultHumidifiers.size()==3);
        assertEquals(humidifier6.getId(),resultHumidifiers.get(0).getId());
        assertEquals(humidifier1.getId(),resultHumidifiers.get(1).getId());
        assertEquals(humidifier2.getId(),resultHumidifiers.get(2).getId());
    }

    @Test
    void findRightHumidifiers3countWithoutType2() {
        creatEntitiesWithoutType();
        humidifierService.saveAll(Stream.of(humidifier1,humidifier2,humidifier3,humidifier4,humidifier5,humidifier6,humidifier7).collect(Collectors.toList()));
        List<Humidifier> resultHumidifiers = humidifierRepository.findDistinctFirst3ByCapacityGreaterThanEqualAndPhaseOrderByCapacity(80,1);
        assertTrue(resultHumidifiers.size()==0);
    }

    @Test
    void findRightHumidifiers3countWithoutType3() {
        creatEntitiesWithoutType();
        humidifierService.saveAll(Stream.of(humidifier1,humidifier2,humidifier3,humidifier4,humidifier5,humidifier6,humidifier7).collect(Collectors.toList()));
        List<Humidifier> resultHumidifiers = humidifierRepository.findDistinctFirst3ByCapacityGreaterThanEqualAndPhaseOrderByCapacity(8,3);
        assertEquals(2,resultHumidifiers.size());
        assertEquals(humidifier4.getId(),resultHumidifiers.get(0).getId());
    }
}