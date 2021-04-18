package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.VaporDistributorDto;
import ru.systemairac.calculator.mapper.ProjectMapper;
import ru.systemairac.calculator.repository.CalculationRepository;
import ru.systemairac.calculator.service.Point;
import ru.systemairac.calculator.service.allinterface.CalculationService;
import ru.systemairac.calculator.service.allinterface.HumidifierService;
import ru.systemairac.calculator.service.allinterface.ProjectService;
import ru.systemairac.calculator.service.allinterface.VaporDistributorService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculationServiceImpl implements CalculationService {

    private final HumidifierService humidifierService;
    private final VaporDistributorService vaporDistributorService;
    private final ProjectMapper mapperProject = ProjectMapper.MAPPER;
    private final CalculationRepository calculationRepository;
    private final ProjectService projectService;

    public CalculationServiceImpl(HumidifierService humidifierService, VaporDistributorService vaporDistributorService, CalculationRepository repository, ProjectService projectService) {
        this.humidifierService = humidifierService;
        this.vaporDistributorService = vaporDistributorService;
        this.calculationRepository = repository;
        this.projectService = projectService;
    }

    @Override
    public List<HumidifierDto> getHumidifiers(TechDataDto techDataDto) {
        return humidifierService.findDtoHumidifiers(techDataDto.getCalcCapacity(), techDataDto.getVoltage(),
                techDataDto.getEnumHumidifierType());
    }

    @Override
    public HashMap<Long, VaporDistributorDto> getDistributors(int width, List<HumidifierDto> humidifiers) {
        List<Long> ids = humidifiers.stream().map(HumidifierDto::getId).collect(Collectors.toList());
        List<Humidifier> humidifierList = humidifierService.findHumidifiersByIds(ids);
        HashMap<Long, Integer> diameters = humidifierService.getAllDiameters(humidifierList);
        return vaporDistributorService.getMapDistributorsByIds(width,diameters,humidifierList);
    }

    @Override
    public Calculation createNewCalculation(TechDataDto techDataDto, Project project) {
        List<Calculation> calculations = project.getCalculations();
        List<Calculation> newCalculationsList = calculations == null ? new ArrayList<>() : new ArrayList<> (calculations);
        Calculation newCalculation = new Calculation();
        newCalculationsList.add(newCalculation);
        return newCalculation;
    }

    @Override
    @Transactional
    public Long save(ProjectDto dto, User user) {
        Calculation calculation = new Calculation();
        Project project;
        Project oldProject = projectService.getOldProjectByTitleAndAddress(user,dto.getTitle(),dto.getAddress());
        if (oldProject==null) {
            project = mapperProject.toProject(dto);
            project.setUser(user);
            projectService.save(project);
            calculation.setProject(project);
        } else {
            calculation.setProject(oldProject);
        }
        return calculationRepository.save(calculation).getId();
    }

    @Override
    public TechDataDto calcPower(TechDataDto techDataDto) {
        Point inPoint = Point.builder()
                .temperature(techDataDto.getTempIn())
                .humidity(techDataDto.getHumIn())
                .altitude(techDataDto.getAltitude())
                .atmospherePressure(techDataDto.getAtmospherePressure())
                .build();
        Point outPoint = Point.builder()
                .temperature(techDataDto.getTempIn())
                .humidity(techDataDto.getHumOut())
                .altitude(techDataDto.getAltitude())
                .atmospherePressure(techDataDto.getAtmospherePressure())
                .build();
        int airFlow = techDataDto.getAirFlow();
        double averageDensity = (inPoint.getDensity()+outPoint.getDensity())/2;
        double capacity = airFlow * averageDensity  * (outPoint.getMoistureContent()-inPoint.getMoistureContent())/1000;
        techDataDto.setCalcCapacity(capacity);
        techDataDto.setAltitude(inPoint.getAltitude());
        techDataDto.setAtmospherePressure(inPoint.getAtmospherePressure());
        return techDataDto;
    }
}
