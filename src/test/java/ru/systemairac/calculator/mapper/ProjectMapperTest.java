package ru.systemairac.calculator.mapper;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.dto.ProjectDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectMapperTest {

    private final FakeGenerator fakeGenerator = new FakeGenerator();
    private final ProjectMapper mapper = ProjectMapper.MAPPER;

    @RepeatedTest(5)
    void toProject() {
        ProjectDto dto = fakeGenerator.fakeProjectDto();
        Project entity = mapper.toProject(dto);
        assertFieldsEqual(entity, dto);
    }

    @RepeatedTest(5)
    void toProjectList() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<ProjectDto> dtoList = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            dtoList.add(fakeGenerator.fakeProjectDto());
        }
        List<Project> entityList = mapper.toProjectList(dtoList);
        assertEquals(NUMBER_OF_ELEMENTS, entityList.size());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            assertFieldsEqual(entityList.get(i), dtoList.get(i));
        }
    }

    @RepeatedTest(5)
    void fromProject() {
        Project entity = fakeGenerator.fakeProject(null);
        ProjectDto dto = mapper.fromProject(entity);
        assertFieldsEqual(entity, dto);
    }

    @RepeatedTest(5)
    void fromProjectList() {
        final int NUMBER_OF_ELEMENTS = 100;
        List<Project> entityList = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            entityList.add(fakeGenerator.fakeProject(null));
        }
        List<ProjectDto> dtoList = mapper.fromProjectList(entityList);
        assertEquals(NUMBER_OF_ELEMENTS, dtoList.size());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            assertFieldsEqual(entityList.get(i), dtoList.get(i));
        }
    }

    private static void assertFieldsEqual(Project entity, ProjectDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAddress(), dto.getAddress());
        assertEquals(entity.getTitle(), dto.getTitle());
    }
}
