package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.TechDataDto;

import java.util.List;

public interface CalculationService {
    TechDataDto calcPower(TechDataDto techDataDto);
    List<HumidifierDto> getHumidifiers(TechDataDto techDataDto);
}
