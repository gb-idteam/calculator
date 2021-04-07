package ru.systemairac.calculator.service.allinterface;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;

import java.util.List;


public interface ProjectService {
    List<ProjectDto> findByUser(User user);
    void delete(Long id);
    void save(Project project);
    ProjectDto findByCalculation(Calculation calculation);
    Project getOldProjectByTitleAndAddress(User user, String title, String address);
}
