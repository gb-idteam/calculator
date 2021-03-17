package ru.systemairac.calculator.service;

import ru.systemairac.calculator.dto.PointDto;

public interface CalculationService {
    double calcPower(double airFlow, PointDto point1, PointDto point2);
}
