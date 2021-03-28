package ru.systemairac.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.service.CalculationService;
import ru.systemairac.calculator.service.ProjectService;
import ru.systemairac.calculator.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final UserService userService;
    private final CalculationService calculationService;
    private final ProjectService projectService;
    private ProjectDto projectDto = new ProjectDto();
    private List<ProjectDto> projects = new ArrayList<>();
    private List<HumidifierDto> humidifiers = new ArrayList<>();
    private TechDataDto techDataDto = new TechDataDto();

    public MainController(
            UserService userService,
            CalculationService calculationService,
            ProjectService projectService
    ) {
        this.userService = userService;
        this.calculationService = calculationService;
        this.projectService = projectService;
    }

    @RequestMapping({"","/"})
    public String index(Model model, Principal principal){
        model.addAttribute("projectDto", projectDto);
        if (projects!=null) {
            projects = projectService.findByUser(
                    userService.findByEmail( principal.getName() ).orElseThrow() // вообще говоря, ненахождение юзера
            );                                                                   // случиться не должно
        }
        model.addAttribute("projects", projects);
        model.addAttribute("himidifiers", humidifiers);
        model.addAttribute("techDataDto", techDataDto);
        return "calculator";
    }


    @PostMapping("/calc")
    public String calcAndGetHumidifier(TechDataDto techDataDto){
        this.techDataDto = calculationService.calcPower(techDataDto);
        humidifiers.addAll(calculationService.getHumidifiers(techDataDto));
        return "redirect:/systemair-ac/";
    }

    @PostMapping("/clear")
    public String clear(){
            this.humidifiers.clear();
            this.techDataDto = new TechDataDto();
            this.projectDto = new ProjectDto();
            return "redirect:/systemair-ac/";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
