package ru.systemairac.calculator.mapper;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.VaporDistributorDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VaporDistributorMapperTest {

    private final FakeGenerator fakeGenerator = new FakeGenerator();
    private final VaporDistributorMapper mapper = VaporDistributorMapper.MAPPER;

    @RepeatedTest(5)
    void toVaporDistributor() {
        VaporDistributorDto dto = fakeGenerator.fakeVaporDistributorDto();
        VaporDistributor entity = mapper.toVaporDistributor(dto);
        assertFieldsEqual(entity, dto);
    }

    @RepeatedTest(5)
    void toVaporDistributorList() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<VaporDistributorDto> dtoList = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            dtoList.add(fakeGenerator.fakeVaporDistributorDto());
        }
        List<VaporDistributor> entityList = mapper.toVaporDistributorList(dtoList);
        assertEquals(NUMBER_OF_ELEMENTS, entityList.size());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            assertFieldsEqual(entityList.get(i), dtoList.get(i));
        }
    }

    @RepeatedTest(5)
    void fromVaporDistributor() {
        VaporDistributor entity = fakeGenerator.fakeVaporDistributor();
        VaporDistributorDto dto = mapper.fromVaporDistributor(entity);
        assertFieldsEqual(entity, dto);
    }

    @RepeatedTest(5)
    void fromVaporDistributorList() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<VaporDistributor> entityList = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            entityList.add(fakeGenerator.fakeVaporDistributor());
        }
        List<VaporDistributorDto> dtoList = mapper.fromVaporDistributorList(entityList);
        assertEquals(NUMBER_OF_ELEMENTS, dtoList.size());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            assertFieldsEqual(entityList.get(i), dtoList.get(i));
        }
    }

    private static void assertFieldsEqual(VaporDistributor entity, VaporDistributorDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getImage(), dto.getImage());
        assertEquals(entity.getArticleNumber(), dto.getArticleNumber());
//        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getLength(), dto.getLength());
        assertEquals(entity.getDiameter(), dto.getDiameter());
        assertEquals(entity.getPrice(), dto.getPrice());
    }
}
