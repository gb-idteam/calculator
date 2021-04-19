package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.systemairac.calculator.dto.HumidifierComponentDto;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.VaporDistributorDto;
import ru.systemairac.calculator.service.allinterface.CalculationService;
import ru.systemairac.calculator.service.allinterface.HumidifierComponentService;
import ru.systemairac.calculator.service.allinterface.HumidifierService;
import ru.systemairac.calculator.service.allinterface.TechDataService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("calculator/humidifier")
@Controller
public class HumidifierController {
    private final CalculationService calculationService;
    private final TechDataService techDataService;
    private final HumidifierComponentService humidifierComponentService;
    private final HumidifierService humidifierService;

    public HumidifierController(CalculationService calculationService, TechDataService techDataService, HumidifierComponentService humidifierComponentService, HumidifierService humidifierService) {
        this.calculationService = calculationService;
        this.techDataService = techDataService;
        this.humidifierComponentService = humidifierComponentService;
        this.humidifierService = humidifierService;
    }

    @PostMapping("/select")
    public String selectHumidifier(@RequestParam(value = "idSelectHumidifier") Long idSelectHumidifier, HttpServletRequest request){
        request.getSession().setAttribute("idSelectHumidifier",idSelectHumidifier);
        return "redirect:/calculator";
    }

    @PostMapping("/calculation")
    public String calcAndGetHumidifier(TechDataDto techDataDto, HttpServletRequest request){
        List<HumidifierDto> humidifiers = new ArrayList<>();
        TechDataDto techData  = calculationService.calcPower(techDataDto);
        humidifiers.addAll(calculationService.getHumidifiers(techDataDto));
        HashMap<Long, VaporDistributorDto> distributors = calculationService.getDistributors(techDataDto.getWidth(),humidifiers);
        HashMap<Long, List<HumidifierComponentDto>> options = humidifierComponentService.getAllComponentByHumidifiers(humidifiers);
        Long techDataDtoId = techDataService.save(techData, (Long) request.getSession().getAttribute("calcId"));
        request.getSession().setAttribute("humidifiers",humidifiers);
        request.getSession().setAttribute("techDataDtoId",techDataDtoId);
        request.getSession().setAttribute("distributors",distributors);
        request.getSession().setAttribute("options",options);
        return "redirect:/calculator";
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

