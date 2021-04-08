package ru.systemairac.calculator.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.*;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.service.allinterface.*;

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
    private Calculation calculation;
    private ProjectDto projectDto = new ProjectDto();
    private EstimateDto estimateDto = new EstimateDto();
    private List<ProjectDto> projects = new ArrayList<>();
    private List<HumidifierDto> humidifiers = new ArrayList<>();
    private HashMap<Long, VaporDistributorDto> distributors = new HashMap<>();
    private HashMap<Long, List<HumidifierComponentDto>> options = new HashMap<>();
    private Long idSelectHumidifier;
    // Для тестирования
    private TechDataDto techDataDto = TechDataDto.builder().
            airFlow(500).
            tempIn(20).
            humIn(1).
            voltage(EnumVoltageType.ONE_PHASE_220V).
            humOut(60).
            build();
    private final PDDocumentService pdDocumentService;

    public MainController(ProjectService projectService, UserService userService, CalculationService calculationService, TechDataService techDataService, ImageService imageService, EstimateService estimateService, HumidifierComponentService humidifierComponentService, HumidifierService humidifierService, FileService fileService, PDDocumentService pdDocumentService) {
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
        init();
    }

    public void init() {
        Humidifier humidifier1 = humidifierService.findHumidifierById(1L);
        Humidifier humidifier3 = humidifierService.findHumidifierById(3L);
        List<HumidifierComponent> options = humidifierComponentService.getAllComponent();
        Image image1 = imageService.findById(1L);
        Image image2 = imageService.findById(2L);
        humidifier1.setHumidifierComponents(options);
        humidifier1.setImage(image1);
        humidifier3.setHumidifierComponents(options);
        humidifier3.setImage(image2);
        humidifierService.save(humidifier1);
        humidifierService.save(humidifier3);
    }

    @RequestMapping({"","/"})
    public String index(Model model, Principal principal){
        if (principal!=null) {
            projects = projectService.findByUser(
                    userService.findByEmail( principal.getName() ).orElseThrow()
            );
        }
        model.addAttribute("projects", projects);
        model.addAttribute("estimate", estimateDto);
        model.addAttribute("himidifiers", humidifiers);
        model.addAttribute("projectDto", projectDto);
        model.addAttribute("idSelectHumidifier", idSelectHumidifier);
        model.addAttribute("techDataDto", techDataDto);
        model.addAttribute("options", options);
        model.addAttribute("distributors", distributors);
        return "calculator";
    }

    @PostMapping("/saveProject")
    public String saveProject(ProjectDto projectDto, Principal principal){
        if (projectDto.getId()==null) {
            User user = userService.findByEmail( principal.getName() ).orElseThrow();
            this.calculation = calculationService.save(projectDto,user);
            this.projectDto = projectService.findByCalculation(calculation);
        }
        return "redirect:/calculator";
    }

    @PostMapping("/selectHumidifier")
    public String selectHumidifier(Model model, Long idSelectHumidifier){
        this.idSelectHumidifier = idSelectHumidifier;
        model.addAttribute("idSelectHumidifier", idSelectHumidifier);
        return "redirect:/calculator";
    }

    @PostMapping("/calc")
    public String calcAndGetHumidifier(TechDataDto techDataDto){
        this.humidifiers.clear();
        this.options.clear();
        this.techDataDto = calculationService.calcPower(techDataDto);
        this.humidifiers.addAll(calculationService.getHumidifiers(techDataDto));
        this.distributors = calculationService.getDistributors(techDataDto.getWidth(),humidifiers);
        this.options = humidifierComponentService.getAllComponentByHumidifiers(humidifiers);
        techDataService.save(techDataDto,this.calculation.getId());
        return "redirect:/calculator";
    }

    @PostMapping("/resultEstimate")
    public String resultEstimate(Principal principal,
                                 @RequestParam(value = "selectedOptions" , required = false) Long[] idSelectedOptions,
                                 @RequestParam(value = "distributor" , required = false) Long idDistributor) throws IOException {
        EstimateDto estimateDto = estimateService.save(calculation.getId(), idSelectHumidifier,idSelectedOptions,idDistributor);
        PDDocument document = pdDocumentService.toPDDocument(userService.getByEmail(principal.getName()),
                projectDto,
                techDataDto,
                estimateDto); //TODO исправить когду будет возможность выбора вентиляторного распределителя
        document.save("result.pdf");
        document.close();
        return "redirect:/calculator";
    }

    @PostMapping("/clear")
    public String clear(){
            this.humidifiers.clear();
            this.techDataDto = new TechDataDto();
            this.calculation = null;
            this.projectDto = new ProjectDto();
            this.humidifiers = new ArrayList<>();
            this.options = new HashMap<>();
            this.distributors = new HashMap<>();
            return "redirect:/calculator";
    }
}
