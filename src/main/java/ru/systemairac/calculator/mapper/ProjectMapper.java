package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.dto.ProjectDto;

import java.util.List;
@Mapper
public interface ProjectMapper {
    ProjectMapper MAPPER = Mappers.getMapper(ProjectMapper.class);

    Project toProject(ProjectDto dto);
    List<Project> toProjectList(List<ProjectDto> projects);

    @InheritInverseConfiguration
    ProjectDto fromProject(Project project);
    List<ProjectDto> fromProjectList(List<Project> projects);
}
