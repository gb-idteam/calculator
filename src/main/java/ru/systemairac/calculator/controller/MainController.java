package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.service.CalculationService;
import ru.systemairac.calculator.service.ProjectService;
import ru.systemairac.calculator.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/calculator")
@Controller
public class MainController {
    private final ProjectService projectService;
    private final UserService userService;
    private final CalculationService calculationService;
    private ProjectDto projectDto = new ProjectDto();
    private Long currentProjectId;
    private List<ProjectDto> projects = new ArrayList<>();
    private List<HumidifierDto> humidifiers = new ArrayList<>();
    private TechDataDto techDataDto = new TechDataDto();

    public MainController(ProjectService projectService, UserService userService, CalculationService calculationService) {
        this.projectService = projectService;
        this.userService = userService;
        this.calculationService = calculationService;
    }

    @RequestMapping({"","/"})
    public String index(Model model, Principal principal){
        model.addAttribute("projectDto", projectDto);
        if (projects.isEmpty() && principal!=null) {
            projects = projectService.findByUser(
                    userService.getByEmail( principal.getName() ) // вообще говоря, ненахождение юзера
            );                                                                   // случиться не должно
        }
        model.addAttribute("projects", projects);
        model.addAttribute("himidifiers", humidifiers);
        model.addAttribute("techDataDto", techDataDto);
        model.addAttribute("projectDto", projectDto);
        return "calculator";
    }

    @PostMapping("/calc")
    public String calcAndGetHumidifier(ProjectDto projectDto, TechDataDto techDataDto){
        this.projectDto = projectDto;
        humidifiers.clear();
        this.techDataDto = calculationService.calcPower(techDataDto);
        humidifiers.addAll(calculationService.getHumidifiers(techDataDto));
        return "redirect:/systemair-ac/calculator";
    }

    @PostMapping("/clear")
    public String clear(){
            this.humidifiers.clear();
            this.techDataDto = new TechDataDto();
            this.projectDto = new ProjectDto();
            return "redirect:/systemair-ac/calculator";
    }

    @PostMapping("/saveProjectInfo")
    public String saveProjectInfoButtonClick(ProjectDto projectDto, Principal principal) {
        this.projectDto = projectDto;
        this.projectDto.setUser(principal.getName());
        if (currentProjectId == null) {
            Long projectSavedId = projectService.save(this.projectDto).getId();
            this.projectDto.setProjectId(projectSavedId);
            currentProjectId = projectSavedId;
            System.out.println(projectSavedId);
        } else {
            this.projectDto.setProjectId(currentProjectId);
            projectService.update(this.projectDto);
        }
        return "redirect:/systemair-ac/calculator";
    }
}
