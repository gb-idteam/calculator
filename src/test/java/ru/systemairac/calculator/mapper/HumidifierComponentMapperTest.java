package ru.systemairac.calculator.mapper;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.HumidifierComponentDto;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.myenum.HumidifierComponentType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HumidifierComponentMapperTest {

    private FakeGenerator fakeGenerator = new FakeGenerator();
    private HumidifierComponentMapper mapper = HumidifierComponentMapper.MAPPER;

    @RepeatedTest(5)
    void toHumidifierComponent() {
        HumidifierComponentDto dto = fakeGenerator.fakeHumidifierComponentDto();
        HumidifierComponent entity = mapper.toHumidifierComponent(dto);
        assertFieldsEqual(entity, dto);
    }

    @RepeatedTest(5)
    void toHumidifierComponentList() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<HumidifierComponentDto> dtoList = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            dtoList.add(fakeGenerator.fakeHumidifierComponentDto());
        }
        List<HumidifierComponent> entityList = mapper.toHumidifierComponentList(dtoList);
        assertEquals(NUMBER_OF_ELEMENTS, entityList.size());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            assertFieldsEqual(entityList.get(i), dtoList.get(i));
        }
    }

    @RepeatedTest(5)
    void fromHumidifierComponent() {
        HumidifierComponent entity = fakeGenerator.fakeHumidifierComponent();
        HumidifierComponentDto dto = mapper.fromHumidifierComponent(entity);
        assertFieldsEqual(entity, dto);
    }

    @RepeatedTest(5)
    void fromHumidifierComponentList() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<HumidifierComponent> entityList = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            entityList.add(fakeGenerator.fakeHumidifierComponent());
        }
        List<HumidifierComponentDto> dtoList = mapper.fromHumidifierComponentList(entityList);
        assertEquals(NUMBER_OF_ELEMENTS, dtoList.size());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            assertFieldsEqual(entityList.get(i), dtoList.get(i));
        }
    }

    private void assertFieldsEqual(HumidifierComponent entity, HumidifierComponentDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getImage(), dto.getImage());
        assertEquals(entity.getArticleNumber(), dto.getArticleNumber());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.isOptional(), dto.isOptional());
        assertEquals(entity.getPrice(), dto.getPrice());
    }
}
