package ru.systemairac.calculator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.*;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.service.allinterface.ProjectService;
import ru.systemairac.calculator.service.allinterface.TechDataService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/calculator")
@Controller
public class MainController {
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final TechDataService techDataService;

    public MainController(ProjectService projectService, UserRepository userRepository, TechDataService techDataService) {
        this.projectService = projectService;
        this.userRepository = userRepository;
        this.techDataService = techDataService;
    }

    @RequestMapping({"","/"})
    public String index(Model model, Principal principal, HttpSession session){
        addProjectsAndConfirmed(model, principal);
        addTechData(model, session);
        addThisProject(model, session);
        addHumidifier(model, session);
        addSelectedHumidifier(model, session);
        addOptions(model, session);
        addDistributors(model, session);
        return "calculator";
    }

    private void addDistributors(Model model, HttpSession session) {
        HashMap<Long, VaporDistributorDto> distributors = (HashMap<Long, VaporDistributorDto>) session.getAttribute("distributors");
        model.addAttribute("distributors", distributors);
    }

    private void addSelectedHumidifier(Model model, HttpSession session) {
        Long idSelectHumidifier = (Long) session.getAttribute("idSelectHumidifier");
        model.addAttribute("idSelectHumidifier", idSelectHumidifier);
    }

    private void addOptions(Model model, HttpSession session) {
        HashMap<Long, List<HumidifierComponentDto>> options = (HashMap<Long, List<HumidifierComponentDto>>) session.getAttribute("options");
        model.addAttribute("options", options);
    }

    private void addHumidifier(Model model, HttpSession session) {
        List<HumidifierDto> humidifiers = (List<HumidifierDto>) session.getAttribute("humidifiers");
        model.addAttribute("humidifiers", humidifiers);
    }

    private void addThisProject(Model model, HttpSession session) {
        ProjectDto projectDto= new ProjectDto();
        Long idProject = (Long) session.getAttribute("projectId");
        if (idProject!=null)
            projectDto = projectService.findById(idProject);
        model.addAttribute("projectDto", projectDto);
    }

    private void addTechData(Model model, HttpSession session) {
        TechDataDto techDataDto = new TechDataDto();
        Long idTechDataDto = (Long) session.getAttribute("techDataDtoId");
        if (idTechDataDto!=null)
            techDataDto = techDataService.findById(idTechDataDto);
        model.addAttribute("techDataDto", techDataDto);
    }

    private void addProjectsAndConfirmed(Model model, Principal principal) {
        boolean isConfirmed = false;
        List<ProjectDto> projects = new ArrayList<>();
        if (principal !=null) {
            User user = userRepository.findByEmail(principal.getName() );
            projects = projectService.findByUser(user);
            isConfirmed = user.isConfirmed();
        }
        model.addAttribute("isConfirmed", isConfirmed);
        model.addAttribute("projects", projects);
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
