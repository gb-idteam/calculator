package ru.systemairac.calculator.service;

import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.PointDto;
import ru.systemairac.calculator.dto.TechDataDto;

import java.util.List;

public interface CalculationService {
    double calcPower(TechDataDto techDataDto);
    List<HumidifierDto> calcAndGetHumidifier(TechDataDto techDataDto);
}
