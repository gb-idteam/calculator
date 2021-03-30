package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.mapper.ProjectMapper;
import ru.systemairac.calculator.repository.ProjectRepository;
import ru.systemairac.calculator.service.allinterface.CalculationService;
import ru.systemairac.calculator.service.allinterface.ProjectService;
import ru.systemairac.calculator.service.allinterface.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper mapper = ProjectMapper.MAPPER;
    private final CalculationService calculationService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService, CalculationService calculationService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.calculationService = calculationService;
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
    @Transactional
    public ProjectDto addProject(ProjectDto projectDto, String email) {
        User user = userService.getByEmail( email );
        if(user == null){
            throw new RuntimeException("User not found. " + email);
        }
        List<Project> projects = user.getProjects();
        Project project = projects.stream()
                .filter(p -> p.getAddress().equals(projectDto.getAddress())
                        && p.getTitle().equals(projectDto.getTitle()))
                .findAny()
                .orElse(null);
        if(project == null){
            project = createNewProject(user,projectDto.getAddress(),projectDto.getTitle());
        } else {
            project.setCalculation(Collections.singletonList(new Calculation()));
            projectRepository.save(project);
        }
        return mapper.fromProject(project);
    }

    @Override
    public  Project createNewProject(User user, String address, String title) {
        List<Project> projects = user.getProjects();
        List<Project> newProjectsList = projects == null ? new ArrayList<>() : new ArrayList<> (projects);
        Project newProject = new Project(null,address,title, Collections.singletonList(new Calculation()),user);
        newProjectsList.add(newProject);
        user.setProjects(newProjectsList);
        userService.save(user);
        return newProject;
    }
}
