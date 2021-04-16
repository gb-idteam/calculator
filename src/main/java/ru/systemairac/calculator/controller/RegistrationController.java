package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.service.allinterface.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @GetMapping("/verification")
    public String verification(Model model,
                               @RequestParam(value = "param1" , required = false) Long idUser,
                               @RequestParam(value = "param2" , required = false) String code) {
        if (idUser!=null && code!=null) {
            User user = userService.getById(idUser).orElseThrow();
            userService.userConfirmation(user, code);
        }
        return "redirect:/calculator";
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public String registerButtonClick(Model model,UserDto userDto) {
        try {
            userService.save(userDto);
            model.addAttribute("verificationMessage",true);
        } catch (RuntimeException e) {
            // пока так?
            return "";
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, Model model) {
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);
        // some other stuff
        return "login";
    }

    @RequestMapping("/login")
    @PreAuthorize("permitAll()")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
