package ru.systemairac.calculator.service;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.PointDto;
import ru.systemairac.calculator.dto.TechDataDto;

import java.util.List;

@Service
public class CalculationServiceImpl implements CalculationService {

    private final HumidifierService humidifierService;

    public CalculationServiceImpl(HumidifierService humidifierService) {
        this.humidifierService = humidifierService;
    }

    @Override
    public List<HumidifierDto> calcAndGetHumidifier(TechDataDto techDataDto) {
        return humidifierService.findHumidifiers(calcPower(techDataDto), techDataDto.getPhase(),
                techDataDto.getHumidifierType(),techDataDto.getTypeMontage());
    }

    @Override
    public double calcPower(TechDataDto techDataDto) {
        PointDto inPoint = PointDto.builder()
                .temperature(techDataDto.getTempIn())
                .humidity(techDataDto.getHumIn())
                .build();
        PointDto outPoint = PointDto.builder()
                .temperature(techDataDto.getTempIn())
                .humidity(techDataDto.getHumIn())
                .build();
        int airFlow = techDataDto.getAirFlow();
        double averageDensity = (inPoint.getDensity()+outPoint.getDensity())/2;
        return airFlow * averageDensity  * (outPoint.getMoistureContent()-inPoint.getMoistureContent())/1000;
    }
}
