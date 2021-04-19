package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.mapper.ProjectMapper;
import ru.systemairac.calculator.repository.CalculationRepository;
import ru.systemairac.calculator.repository.ProjectRepository;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.service.allinterface.ProjectService;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final CalculationRepository calculationRepository;
    private final UserRepository userRepository;
    private final ProjectMapper mapper = ProjectMapper.MAPPER;

    public ProjectServiceImpl(ProjectRepository projectRepository, CalculationRepository calculationRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.calculationRepository = calculationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectDto> findByUser(User user) {
        return mapper.fromProjectList(projectRepository.findByUser(user));
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public ProjectDto findById(Long id) {
        return mapper.fromProject(projectRepository.findById(id).orElseThrow());
    }

    @Override
    public Long findByCalculation(Long calcId) {
        Project project = calculationRepository.findById(calcId).orElseThrow().getProject();
        return project.getId();
    }

    @Override
    public Project getOldProjectByTitleAndAddress(User user, String title, String address) {
        return projectRepository.findByUserAndTitleAndAddress(user,title,address);

    }
}
