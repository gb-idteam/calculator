package ru.systemairac.calculator.service.allinterface;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;

import java.util.List;


public interface ProjectService {
    List<ProjectDto> findByUser(User user);
    void delete(Long id);
    ProjectDto save(ProjectDto projectDto,User user);
    ProjectDto addProject(ProjectDto projectDto, String name);
    Project createNewProject(User user, String address, String title);
    void saveTechData(ProjectDto projectDto, TechDataDto techDataDto);
}
