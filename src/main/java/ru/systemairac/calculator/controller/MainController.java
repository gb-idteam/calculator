package ru.systemairac.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.dto.HumidifierComponentDto;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.myenum.HumidifierComponentType;
import ru.systemairac.calculator.service.CalculationService;
import ru.systemairac.calculator.service.ProjectService;
import ru.systemairac.calculator.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/calculator")
@Controller
public class MainController {
    private final ProjectService projectService;
    private final UserService userService;
    private final CalculationService calculationService;
    private ProjectDto projectDto = new ProjectDto();
    private List<HumidifierComponentDto> options = new ArrayList<>();
    private List<ProjectDto> projects = new ArrayList<>();
    private List<HumidifierDto> humidifiers = new ArrayList<>();
    // Для тестирования
    private TechDataDto techDataDto = TechDataDto.builder().
            airFlow(500).
            tempIn(20).
            humIn(1).
            humOut(60).
            build();
    public MainController(ProjectService projectService, UserService userService, CalculationService calculationService) {
        this.projectService = projectService;
        this.userService = userService;
        this.calculationService = calculationService;
    }

    @RequestMapping({"","/"})
    public String index(Model model, Principal principal){
        model.addAttribute("projectDto", projectDto);
        if (principal!=null) {
            projects = projectService.findByUser(
                    userService.getByEmail( principal.getName() )
            );
        }
        model.addAttribute("projects", projects);
        model.addAttribute("projectDto", projectDto);
        model.addAttribute("himidifiers", humidifiers);
        model.addAttribute("selectedHumidifier", new HumidifierDto());
        model.addAttribute("techDataDto", techDataDto);
        return "calculator";
    }

    @PostMapping("/saveProject")
    public String saveProject(ProjectDto projectDto, Principal principal){
        this.projectDto = projectService.addProject(projectDto, principal.getName());
        return "redirect:/systemair-ac/calculator";
    }

    @PostMapping("/selectHumidifier")
    public String selectHumidifier(Model model, HumidifierDto humidifierDto){
            this.options.add(new HumidifierComponentDto(1L,"art", HumidifierComponentType.CYLINDER_CASING,true,new BigDecimal(10)));
            model.addAttribute("options", options);
        return "redirect:/systemair-ac/calculator";
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
}
