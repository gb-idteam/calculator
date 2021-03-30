package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.service.allinterface.HumidifierService;

import java.util.List;

@RequestMapping("/humidifier")
@Controller
public class HumidifierController {

    private HumidifierService humidifierService;

    public HumidifierController(HumidifierService humidifierService) {
        this.humidifierService = humidifierService;
    }

    @GetMapping
    public String humidifierList(Model model) {
        List<HumidifierDto> humidifierDtoList = humidifierService.getAll();
        model.addAttribute("humidifiers", humidifierDtoList);
        return "humidifiers";
    }

    @GetMapping("/{id}")
    public String getHumidifier(@PathVariable Long id, Model model) {
        HumidifierDto humidifierDto = humidifierService.findById(id);
        model.addAttribute("humidifier", humidifierDto);
        return "humidifier-form";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit{id]")
    public String editFormForHumidifier(@PathVariable Long id, Model model) {
        model.addAttribute("humidifier", humidifierService.findById(id));
        return "edit-humidifier";
    }

    @PostMapping("/edit")
    public String modifyHumidifier(HumidifierDto humidifierDto) {
        humidifierService.save(humidifierDto);
        return "redirect:/humidifier";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newHumidifier(Model model) {
        model.addAttribute("humidifier", new HumidifierDto());
        return "new-humidifier";
    }

    @PostMapping(value = "/new")
    public String saveHumidifier(HumidifierDto dto) {
        humidifierService.save(dto);
        return "redirect:/humidifier";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete{id}")
    public void deleteHumidifier(@PathVariable Long id) {
        humidifierService.deleteById(id);
//        return "redirect:/humidifier";
    }
}

