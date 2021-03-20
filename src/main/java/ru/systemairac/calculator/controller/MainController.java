package ru.systemairac.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.InfoDto;
import ru.systemairac.calculator.dto.PointDto;
import ru.systemairac.calculator.dto.TechDataDto;

@Controller
public class MainController {

    @RequestMapping({"","/"})
    public String index(Model model){
        model.addAttribute("infoDto", new InfoDto());
        model.addAttribute("humidifierDto", new HumidifierDto());
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
