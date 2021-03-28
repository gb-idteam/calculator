package ru.systemairac.calculator.service;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.TechDataDto;

import java.util.List;

@Service
public class CalculationServiceImpl implements CalculationService {

    private final HumidifierService humidifierService;

    public CalculationServiceImpl(HumidifierService humidifierService) {
        this.humidifierService = humidifierService;
    }

    @Override
    public List<HumidifierDto> getHumidifiers(TechDataDto techDataDto) {
        return humidifierService.findHumidifiersNamed(techDataDto.getCalcCapacity(), techDataDto.getPhase(),
                techDataDto.getEnumHumidifierType());
    }

    @Override
    public TechDataDto calcPower(TechDataDto techDataDto) {
        Point inPoint = Point.builder()
                .temperature(techDataDto.getTempIn())
                .humidity(techDataDto.getHumIn())
                .build();
        Point outPoint = Point.builder()
                .temperature(techDataDto.getTempIn())
                .humidity(techDataDto.getHumOut())
                .build();
        int airFlow = techDataDto.getAirFlow();
        double averageDensity = (inPoint.getDensity()+outPoint.getDensity())/2;
        double capacity = airFlow * averageDensity  * (outPoint.getMoistureContent()-inPoint.getMoistureContent())/1000;
        techDataDto.setCalcCapacity(capacity);
        return techDataDto;

    }
}
