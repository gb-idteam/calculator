package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.VaporDistributorDto;

import java.util.HashMap;
import java.util.List;

public interface CalculationService {
    TechDataDto calcPower(TechDataDto techDataDto);
    List<HumidifierDto> getHumidifiers(TechDataDto techDataDto);
    HashMap<Long, VaporDistributorDto> getDistributors(int width, List<HumidifierDto> humidifiers);
    Calculation createNewCalculation(TechDataDto techDataDto, Project project);
    Long save(ProjectDto project, User user);
}
