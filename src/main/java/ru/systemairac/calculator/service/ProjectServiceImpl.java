package ru.systemairac.calculator.service;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.mapper.ProjectMapper;
import ru.systemairac.calculator.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper = ProjectMapper.MAPPER;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project save(ProjectDto projectDto) {
        return projectRepository.save(mapper.toHumidifier(projectDto));
    }

    @Override
    public List<ProjectDto> findByUser(User user) {
        return mapper.fromProjectList(projectRepository.findByUser(user));
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}
