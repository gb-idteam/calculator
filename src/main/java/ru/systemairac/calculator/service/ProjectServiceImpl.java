package ru.systemairac.calculator.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.mapper.ProjectMapper;
import ru.systemairac.calculator.repository.ProjectRepository;
import ru.systemairac.calculator.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper = ProjectMapper.MAPPER;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /**
     * Сохраняет новый проект.
     * @param projectDto Сохраняемый проект. projectId должен быть null
     * @throws IllegalArgumentException В случае, если projectDto.projectId != null
     * @throws RuntimeException Если User с email == projectDto.user не найден
     * @return Project, который сохранён в репозиторий (с id).
     */
    @Override
    public Project save(ProjectDto projectDto) {
        if (projectDto.getProjectId() != null)
            throw new IllegalArgumentException("projectDto.projectId != null");
        User u = userRepository.findFirstByEmail(projectDto.getUser()).orElseThrow(() -> new RuntimeException("Не найден пользователь!!"));
        Project project = mapper.toProject(projectDto);
        project.setUser(u);
        return projectRepository.save(project);
    }

    /**
     * Обновляет информацию о проекте в репозитории.
     * @param projectDto Обновляемый проект. projectId и user должны соответствовать имеющемуся проекту.
     * @throws IllegalArgumentException Если projectDto.projectId == null, или если имеющийся в репозитории проект
     *  имеет другого пользователя.
     * @throws NoSuchElementException Если не найден Project с id = projectDto.projectId.
     * @return Project, который сохранён в репозиторий (с id).
     */
    @Override
    public Project update(ProjectDto projectDto) {
        if (projectDto.getProjectId() == null) {
            throw new IllegalArgumentException("projectDto.projectId == null");
        }
        Project existingProject = projectRepository.findById(projectDto.getProjectId()).orElseThrow();
        if (! existingProject.getUser().getEmail().equals(projectDto.getUser()) ) {
            throw new IllegalArgumentException("projectDto.projectId == null");
        }
        Project updated = mapper.toProject(projectDto);
        updated.setUser(existingProject.getUser());
        return projectRepository.save(updated);
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
