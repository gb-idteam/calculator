package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.*;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.service.allinterface.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private final TechDataService techDataService;

    public MainController(ProjectService projectService, UserService userService, TechDataService techDataService) {
        this.projectService = projectService;
        this.userService = userService;
        this.techDataService = techDataService;
    }

    @RequestMapping({"","/"})
    public String index(Model model, Principal principal, HttpSession session){
        boolean isConfirmed = false;
        List<ProjectDto> projects = new ArrayList<>();
        if (principal!=null) {
            User user = userService.findByEmail( principal.getName() ).orElseThrow();
            projects = projectService.findByUser(user);
            isConfirmed = user.isConfirmed();
        }
        // Для тестирования
        TechDataDto techDataDto = TechDataDto.builder().
                airFlow(500).
                tempIn(20.0).
                humIn(1.0).
                voltage(EnumVoltageType.ONE_PHASE_220V).
                humOut(60.0).
                build();
        List<HumidifierDto> humidifiers = (List<HumidifierDto>) session.getAttribute("humidifiers");
        HashMap<Long, List<HumidifierComponentDto>> options = (HashMap<Long, List<HumidifierComponentDto>>) session.getAttribute("options");
        HashMap<Long, VaporDistributorDto> distributors = (HashMap<Long, VaporDistributorDto>) session.getAttribute("distributors");
        Long idSelectHumidifier = (Long) session.getAttribute("idSelectHumidifier");
        ProjectDto projectDto= new ProjectDto();
        Long idProject = (Long) session.getAttribute("projectId");
        if (idProject!=null)
            projectDto = projectService.findById(idProject);
        Long idTechDataDto = (Long) session.getAttribute("techDataDtoId");
        if (idTechDataDto!=null)
            techDataDto = techDataService.findById(idTechDataDto);
        model.addAttribute("isConfirmed", isConfirmed);
        model.addAttribute("projects", projects);
        model.addAttribute("humidifiers", humidifiers);
        model.addAttribute("projectDto", projectDto);
        model.addAttribute("idSelectHumidifier", idSelectHumidifier);
        model.addAttribute("techDataDto", techDataDto);
        model.addAttribute("options", options);
        model.addAttribute("distributors", distributors);
        return "calculator";
    }

    @PostMapping("/clear")
    public String clear(HttpServletRequest request){
        request.getSession().setAttribute("humidifierId",null);
        request.getSession().setAttribute("techDataDto",new TechDataDto());
        request.getSession().setAttribute("projectId",null);
        request.getSession().setAttribute("calcId",null);
        request.getSession().setAttribute("techDataDtoId",null);
        request.getSession().setAttribute("humidifiers",new ArrayList<>());
        request.getSession().setAttribute("distributors",new HashMap<>());
        request.getSession().setAttribute("options",new HashMap<>());
            return "redirect:/calculator";
    }
}
