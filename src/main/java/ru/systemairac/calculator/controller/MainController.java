package ru.systemairac.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private List<HumidifierDto> himidifiers = new ArrayList<>();

    @RequestMapping({"","/"})
    public String index(Model model){
        // Тестовая dtoшка
        HumidifierDto humidifierDto = HumidifierDto.builder()
                .id(1L)
                .articleNumber("1")
                .electricPower(5)
                .maxVaporOutput(20)
                .phase(3)
                .vaporPipeDiameter(25)
                .numberOfCylinders(1)
                .voltage(380)
                .price(BigDecimal.valueOf(1000))
                .build();
        himidifiers.add(humidifierDto);
        model.addAttribute("projectDto", new ProjectDto());
        model.addAttribute("himidifiers", himidifiers);
        model.addAttribute("techDataDto", new TechDataDto());
        return "calculator";
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
