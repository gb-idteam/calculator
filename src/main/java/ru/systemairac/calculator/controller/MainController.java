package ru.systemairac.calculator.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.*;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.repository.ProjectRepository;
import ru.systemairac.calculator.service.allinterface.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    private final CalculationService calculationService;
    private final TechDataService techDataService;
    private final ImageService imageService;
    private final EstimateService estimateService;
    private final HumidifierComponentService humidifierComponentService;
    private final HumidifierService humidifierService;
    private final FileService fileService;
    private final PDDocumentService pdDocumentService;

    public MainController(ProjectService projectService, UserService userService, CalculationService calculationService, TechDataService techDataService, ProjectRepository projectRepository, ImageService imageService, EstimateService estimateService, HumidifierComponentService humidifierComponentService, HumidifierService humidifierService, FileService fileService, PDDocumentService pdDocumentService) {
        this.projectService = projectService;
        this.userService = userService;
        this.calculationService = calculationService;
        this.techDataService = techDataService;
        this.imageService = imageService;
        this.estimateService = estimateService;
        this.humidifierComponentService = humidifierComponentService;
        this.humidifierService = humidifierService;
        this.fileService = fileService;
        this.pdDocumentService = pdDocumentService;
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

    @PostMapping("/saveProject")
    public String saveProject(ProjectDto projectDto, Principal principal, HttpServletRequest request){
        if (projectDto.getId()==null) {
            User user = userService.findByEmail( principal.getName() ).orElseThrow();
            Long calcId = calculationService.save(projectDto,user);
            Long projectId = projectService.findByCalculation(calcId);
            request.getSession().setAttribute("calcId",calcId);
            request.getSession().setAttribute("projectId",projectId);
        }
        return "redirect:/calculator";
    }

    @PostMapping("/selectProject")
    public String selectProject(Long idSelectedProject, Principal principal, HttpServletRequest request){
        if (idSelectedProject!=null) {
            User user = userService.findByEmail( principal.getName() ).orElseThrow();
            ProjectDto projectDto = projectService.findById(idSelectedProject);
            Long calcId = calculationService.save(projectDto,user);
            request.getSession().setAttribute("calcId",calcId);
            request.getSession().setAttribute("projectId",idSelectedProject);
        }
        return "redirect:/calculator";
    }

    @PostMapping("/selectHumidifier")
    public String selectHumidifier(@RequestParam(value = "idSelectHumidifier") Long idSelectHumidifier, HttpServletRequest request){
        request.getSession().setAttribute("idSelectHumidifier",idSelectHumidifier);
        return "redirect:/calculator";
    }

    @PostMapping("/calc")
    public String calcAndGetHumidifier(TechDataDto techDataDto, HttpServletRequest request){
        List<HumidifierDto> humidifiers = new ArrayList<>();
        HashMap<Long, List<HumidifierComponentDto>> options;
        HashMap<Long, VaporDistributorDto> distributors;
        TechDataDto techData  = calculationService.calcPower(techDataDto);
        humidifiers.addAll(calculationService.getHumidifiers(techDataDto));
        distributors = calculationService.getDistributors(techDataDto.getWidth(),humidifiers);
        options = humidifierComponentService.getAllComponentByHumidifiers(humidifiers);
        Long techDataDtoId = techDataService.save(techData, (Long) request.getSession().getAttribute("calcId"));
        request.getSession().setAttribute("humidifiers",humidifiers);
        request.getSession().setAttribute("techDataDtoId",techDataDtoId);
        request.getSession().setAttribute("distributors",distributors);
        request.getSession().setAttribute("options",options);
        return "redirect:/calculator";
    }

    @PostMapping("/estimate")
    public String resultEstimate(Principal principal,
                                 HttpServletRequest request,
                                 @RequestParam(value = "selectedOptions" , required = false) Long[] idSelectedOptions,
                                 @RequestParam(value = "selectedDistributor" , required = false) Long idDistributor) throws IOException {
        Long idSelectHumidifier = (Long) request.getSession().getAttribute("idSelectHumidifier");
        Long idCalculation = (Long) request.getSession().getAttribute("calcId");
        Long idProject = (Long) request.getSession().getAttribute("projectId");
        Long idTechDataDto = (Long) request.getSession().getAttribute("techDataDtoId");
        if (idSelectHumidifier==null || idCalculation==null || idProject==null || idTechDataDto==null) {
            throw new NullPointerException(
                    String.format("Нулевое значение одного из параметров: " +
                            "idSelectHumidifier= %d или idProject=%d или idCalculation=%d или idTechDataDto=%d",
                            idSelectHumidifier,
                            idProject,
                            idCalculation,
                            idTechDataDto));
        }
        TechDataDto techDataDto = techDataService.findById(idTechDataDto);
        ProjectDto projectDto = projectService.findById(idProject);
        EstimateDto estimateDto = estimateService.save(idCalculation,idSelectHumidifier,idSelectedOptions,idDistributor);
        PDDocument document = pdDocumentService.toPDDocument(userService.getByEmail(principal.getName()),
                projectDto,
                techDataDto,
                estimateDto);
        String path = fileService.savePDDocument(document,projectDto.getId());
        return "redirect:/file/" + path;
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
