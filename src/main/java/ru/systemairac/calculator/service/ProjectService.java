package ru.systemairac.calculator.service;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;

import java.util.List;


public interface ProjectService {
    Project save(ProjectDto userDto);
    List<ProjectDto> findByUser(User user);
    void delete(Long id);
}
