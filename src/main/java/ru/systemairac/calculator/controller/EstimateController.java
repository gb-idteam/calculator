package ru.systemairac.calculator.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.systemairac.calculator.dto.EstimateDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.service.allinterface.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@RequestMapping("calculator/estimate")
@Controller
public class EstimateController {
    private final UserService userService;
    private final TechDataService techDataService;
    private final ProjectService projectService;
    private final EstimateService estimateService;
    private final PDDocumentService pdDocumentService;
    private final FileService fileService;
    private static final String SHOW="show";
    private static final String SEND="send";
    private static final String SHOW_AND_SEND="show-and-send";

    public EstimateController(UserService userService, TechDataService techDataService, ProjectService projectService, EstimateService estimateService, PDDocumentService pdDocumentService, FileService fileService) {
        this.userService = userService;
        this.techDataService = techDataService;
        this.projectService = projectService;
        this.estimateService = estimateService;
        this.pdDocumentService = pdDocumentService;
        this.fileService = fileService;
    }

    @PostMapping("")
    public String resultEstimate(Principal principal,
                                 HttpServletRequest request,
                                 @RequestParam(value="action") String action,
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
        switch (action) {
            case SHOW:
                return "redirect:/file/" +SHOW+ "/" + path;
            case SEND:
                return "redirect:/file/" +SEND+ "/" + path;
            case SHOW_AND_SEND:
                return "redirect:/file/" +SHOW_AND_SEND+ "/" + path;
        }
        return "redirect:/calculator/estimate";
    }
}
