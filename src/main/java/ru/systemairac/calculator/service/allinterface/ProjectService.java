package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;

import java.util.List;


public interface ProjectService {
    List<ProjectDto> findByUser(User user);
    void delete(Long id);
    void save(Project project);
    ProjectDto findById(Long id);
    Long findByCalculation(Long calcId);
    Project getOldProjectByTitleAndAddress(User user, String title, String address);
}
