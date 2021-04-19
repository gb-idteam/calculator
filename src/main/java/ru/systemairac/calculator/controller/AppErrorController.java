package ru.systemairac.calculator.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AppErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            String message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();
            model.addAttribute("message", message);
            model.addAttribute("status", statusCode);
            return "myError";
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
