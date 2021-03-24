package ru.systemairac.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculationServiceImpl implements CalculationService {

    @Override
    public double calcPower(double airFlow, Point inPoint, Point outPoint) {
        double averageDensity = (inPoint.getDensity()+outPoint.getDensity())/2;
        return airFlow * averageDensity  * (outPoint.getMoistureContent()-inPoint.getMoistureContent())/1000;
    }
}
