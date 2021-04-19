package ru.systemairac.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.repository.CalculationRepository;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.repository.VaporDistributorRepository;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.service.allinterface.CalculationService;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculationServiceTest {

    @Autowired
    private CalculationService service;

    @Autowired
    private CalculationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HumidifierRepository humidifierRepository;

    @Autowired
    private VaporDistributorRepository vaporDistributorRepository;

    private final FakeGenerator fakeGenerator = new FakeGenerator();
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        userRepository.deleteAll();
        humidifierRepository.deleteAll();
        vaporDistributorRepository.deleteAll();
    }

    @RepeatedTest(10)
    void getHumidifiers() {
        final int MAX_NUMBER_OF_ELEMENTS = 250;
        List<Humidifier> list = fakeGenerator.fakeHumidifierList(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        list.forEach(h -> h.setId(null));
        HumidifierServiceTest.removeDuplicateCapacityOrArticle(list);
        humidifierRepository.saveAll(list);

        TechDataDto techDataDto = fakeGenerator.fakeTechDataDto();
        techDataDto.setId(null);
        techDataDto.setCalcCapacity(random.nextDouble() * 120);
        techDataDto.setVoltage(EnumVoltageType.values()[random.nextInt(EnumVoltageType.values().length)]);
        techDataDto.setEnumHumidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)]);

//        list.forEach(System.out::println);

        Humidifier[] expected = list.stream()
                .filter(h -> h.getCapacity() >= techDataDto.getCalcCapacity()
                        && h.getVoltage() == techDataDto.getVoltage()
                        && h.getHumidifierType() == techDataDto.getEnumHumidifierType()

                ).sorted(Comparator.comparingDouble(Humidifier::getCapacity))
                .limit(3)
                .toArray(Humidifier[]::new);
        HumidifierDto[] actual =
                service.getHumidifiers(techDataDto)
                        .toArray(new HumidifierDto[0]);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            HumidifierServiceTest.assertDtoFieldsEqual(expected[i], actual[i]);
        }
    }

    @Test
    void getHumidifiers_shouldNotChangeDto() {
        TechDataDto dto = fakeGenerator.fakeTechDataDto();
        TechDataDto clone = TechDataDto.builder()
                .width(dto.getWidth())
                .voltage(dto.getVoltage())
                .typeMontage(dto.getTypeMontage())
                .tempIn(dto.getTempIn())
                .height(dto.getHeight())
                .humOut(dto.getHumOut())
                .humIn(dto.getHumIn())
                .enumHumidifierType(dto.getEnumHumidifierType())
                .calcCapacity(dto.getCalcCapacity())
                .airFlow(dto.getAirFlow())
                .id(dto.getId())
                .build();
        assertEquals(dto, clone);
        service.getHumidifiers(dto);
        assertEquals(clone, dto);
    }

    // нужно понять, что такое расход воздуха (airFlow)
    @Test
    void calcPower() {
        TechDataDto techDataDto = fakeGenerator.fakeTechDataDto();

        techDataDto.setId(null);
        techDataDto.setTempIn(20.0);
        techDataDto.setHumIn(30.0);
        techDataDto.setHumOut(75.0);
        techDataDto.setAirFlow(1234); // в предположении, что имеется в виду расход воздуха по объёму (м^3 / ч)

        TechDataDto techDataDto2 = TechDataDto.builder()
                .id(techDataDto.getId())
                .airFlow(techDataDto.getAirFlow())
                .calcCapacity(techDataDto.getCalcCapacity())
                .enumHumidifierType(techDataDto.getEnumHumidifierType())
                .humIn(techDataDto.getHumIn())
                .humOut(techDataDto.getHumOut())
                .height(techDataDto.getHeight())
                .tempIn(techDataDto.getTempIn())
                .typeMontage(techDataDto.getTypeMontage())
                .voltage(techDataDto.getVoltage())
                .width(techDataDto.getWidth())
                .build();

        techDataDto = service.calcPower(techDataDto);

        // данные получены из приложения softhvac.ru (W = 10.6 кг/ч)
        // FIXME: заменить на сравнение с некоторой погрешностью
        assertEquals("10.6", String.format("%.1f", techDataDto.getCalcCapacity()));
    }
}
