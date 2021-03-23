package ru.systemairac.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.InfoDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.service.CalculationService;
import ru.systemairac.calculator.service.UserService;
import ru.systemairac.calculator.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final UserService userService;
    private final CalculationService calculationService;

    private InfoDto infoDto = new InfoDto();
    private List<HumidifierDto> humidifiers = new ArrayList<>();
    private TechDataDto techDataDto = new TechDataDto();
    private Double power;

    public MainController(UserService userService, CalculationService calculationService) {
        this.userService = userService;
        this.calculationService = calculationService;
    }



    @RequestMapping({"","/"})
    public String index(Model model){
        model.addAttribute("infoDto", infoDto);
        model.addAttribute("himidifiers", humidifiers);
        model.addAttribute("techDataDto", techDataDto);
        return "calculator";
    }

    @PostMapping("/calc")
    public String calcAndGetHumidifier(InfoDto infoDto, TechDataDto techDataDto){
        this.infoDto = infoDto;
        this.techDataDto = techDataDto;
        this.power = calculationService.calcPower(techDataDto);
        humidifiers.addAll(calculationService.calcAndGetHumidifier(techDataDto));
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
