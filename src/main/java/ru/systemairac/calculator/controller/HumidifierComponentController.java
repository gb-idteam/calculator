package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.service.allinterface.HumidifierComponentService;

import java.util.List;

@RequestMapping("/humidifier-component")
@Controller
public class HumidifierComponentController {

    private HumidifierComponentService componentService;

    public HumidifierComponentController(HumidifierComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public String componentList(Model model) {
        List<HumidifierComponent> humidifierComponents = componentService.getAllComponent();
        model.addAttribute("components", humidifierComponents);
        return "components";
    }

    @GetMapping("/{id}")
    public String getComponent(@PathVariable Long id, Model model) {
        HumidifierComponent humidifierComponent = componentService.findById(id);
        model.addAttribute("component", humidifierComponent);
        return "component-form";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit{id]")
    public String editFormForComponent(@PathVariable Long id, Model model) {
        model.addAttribute("component", componentService.findById(id));
        return "edit-component";
    }

    @PostMapping("/edit")
    public String modifyComponent(HumidifierComponent humidifierComponent) {
        componentService.save(humidifierComponent);
        return "redirect:/humidifier-component";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newComponent(Model model) {
        model.addAttribute("component", new HumidifierComponent());
        return "new-component";
    }

    @PostMapping(value = "/new")
    public String saveComponent(HumidifierComponent humidifierComponent) {
        componentService.save(humidifierComponent);
        return "redirect:/humidifier-component";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete{id}")
    public void deleteComponent(@PathVariable Long id) {
        componentService.deleteById(id);
//        return "redirect:/humidifier-component";
    }

}
