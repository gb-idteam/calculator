package ru.systemairac.calculator.mapper;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HumidifierMapperTest {

    private final FakeGenerator fakeGenerator = new FakeGenerator();
    private final HumidifierMapper mapper = HumidifierMapper.MAPPER;

    @RepeatedTest(5)
    void toHumidifier() {
        HumidifierDto dto = fakeGenerator.fakeHumidifierDto();
        Humidifier entity = mapper.toHumidifier(dto);
        assertFieldsEqual(entity, dto);
    }

    @RepeatedTest(5)
    void toHumidifierList() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<HumidifierDto> dtoList = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            dtoList.add(fakeGenerator.fakeHumidifierDto());
        }
        List<Humidifier> entityList = mapper.toHumidifierList(dtoList);
        assertEquals(NUMBER_OF_ELEMENTS, entityList.size());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            assertFieldsEqual(entityList.get(i), dtoList.get(i));
        }
    }

    @RepeatedTest(5)
    void fromHumidifier() {
        Humidifier entity = fakeGenerator.fakeHumidifier();
        HumidifierDto dto = mapper.fromHumidifier(entity);
        assertFieldsEqual(entity, dto);
    }

    @RepeatedTest(5)
    void fromHumidifierList() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<Humidifier> entityList = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            entityList.add(fakeGenerator.fakeHumidifier());
        }
        List<HumidifierDto> dtoList = mapper.fromHumidifierList(entityList);
        assertEquals(NUMBER_OF_ELEMENTS, dtoList.size());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            assertFieldsEqual(entityList.get(i), dtoList.get(i));
        }
    }

    private static void assertFieldsEqual(Humidifier entity, HumidifierDto dto) {
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
}
