package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.systemairac.calculator.dto.HumidifierComponentDto;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.HumidifierComponentType;
import ru.systemairac.calculator.service.allinterface.CalculationService;
import ru.systemairac.calculator.service.allinterface.HumidifierComponentService;
import ru.systemairac.calculator.service.allinterface.ProjectService;
import ru.systemairac.calculator.service.allinterface.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping("calculator")
@Controller
public class MainController {
    private final ProjectService projectService;
    private final UserService userService;
    private final CalculationService calculationService;
    private final HumidifierComponentService humidifierComponentService;

    private ProjectDto projectDto = new ProjectDto();
    private List<ProjectDto> projects = new ArrayList<>();
    private List<HumidifierDto> humidifiers = new ArrayList<>();
    private HashMap<Long, List<HumidifierComponentDto>> options = new HashMap<>();
    private Long idSelectHumidifier;
    // Для тестирования
    private TechDataDto techDataDto = TechDataDto.builder().
            airFlow(500).
            tempIn(20).
            humIn(1).
            phase(1).
            humOut(60).
            build();

    public MainController(ProjectService projectService, UserService userService, CalculationService calculationService, HumidifierComponentService humidifierComponentService) {
        this.projectService = projectService;
        this.userService = userService;
        this.calculationService = calculationService;
        this.humidifierComponentService = humidifierComponentService;
        //Для теста
        {
            List<HumidifierComponentDto> list = new ArrayList<>();
            list.add(new HumidifierComponentDto(1L, "art1", HumidifierComponentType.CYLINDER_CASING, true, new BigDecimal(150)));
            list.add(new HumidifierComponentDto(2L, "art2", HumidifierComponentType.LEAK_SENSOR, true, new BigDecimal(150)));
            options.put(2L, list);
        }
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
        model.addAttribute("idSelectHumidifier", idSelectHumidifier);
        model.addAttribute("techDataDto", techDataDto);
        model.addAttribute("options", options);
        return "calculator";
    }

    @PostMapping("/saveProject")
    public String saveProject(ProjectDto projectDto, Principal principal){
        this.projectDto = projectService.addProject(projectDto, principal.getName());
        return "redirect:/calculator";
    }

    @PostMapping("/selectHumidifier")
    public String selectHumidifier(Model model, Long idSelectHumidifier){
        this.idSelectHumidifier = idSelectHumidifier;
        model.addAttribute("idSelectHumidifier", idSelectHumidifier);
        return "redirect:/calculator";
    }

    @PostMapping("/calc")
    public String calcAndGetHumidifier(ProjectDto projectDto, TechDataDto techDataDto){
        this.projectDto = projectDto;
        humidifiers.clear();
        options.clear();
        this.techDataDto = calculationService.calcPower(techDataDto);
        humidifiers.addAll(calculationService.getHumidifiers(techDataDto));
        this.options = humidifierComponentService.getAllComponentByHumidifiers(humidifiers);
        return "redirect:/calculator";
    }

    @PostMapping("/clear")
    public String clear(){
            this.humidifiers.clear();
            this.techDataDto = new TechDataDto();
            this.projectDto = new ProjectDto();
            return "redirect:/calculator";
    }
}
