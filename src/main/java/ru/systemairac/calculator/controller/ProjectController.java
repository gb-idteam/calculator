package ru.systemairac.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.service.allinterface.CalculationService;
import ru.systemairac.calculator.service.allinterface.ProjectService;
import ru.systemairac.calculator.service.allinterface.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
@RequestMapping("calculator/project")
@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final CalculationService calculationService;

    public ProjectController(ProjectService projectService, UserService userService, CalculationService calculationService) {
        this.projectService = projectService;
        this.userService = userService;
        this.calculationService = calculationService;
    }

    @PostMapping("/save")
    public String saveProject(ProjectDto projectDto, Principal principal, HttpServletRequest request){
        if (projectDto.getId()==null) {
            User user = userService.findByEmail( principal.getName() ).orElseThrow();
            Long calcId = calculationService.save(projectDto,user);
            Long projectId = projectService.findByCalculation(calcId);
            request.getSession().setAttribute("calcId",calcId);
            request.getSession().setAttribute("projectId",projectId);
        }
        return "redirect:/calculator";
    }

    @PostMapping("/select")
    public String selectProject(Long idSelectedProject, Principal principal, HttpServletRequest request){
        if (idSelectedProject!=null) {
            User user = userService.findByEmail( principal.getName() ).orElseThrow();
            ProjectDto projectDto = projectService.findById(idSelectedProject);
            Long calcId = calculationService.save(projectDto,user);
            request.getSession().setAttribute("calcId",calcId);
            request.getSession().setAttribute("projectId",idSelectedProject);
        }
        return "redirect:/calculator";
    }
}
