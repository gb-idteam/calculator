package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.VaporDistributorDto;
import ru.systemairac.calculator.mapper.HumidifierMapper;
import ru.systemairac.calculator.mapper.VaporDistributorMapper;
import ru.systemairac.calculator.service.Point;
import ru.systemairac.calculator.service.allinterface.CalculationService;
import ru.systemairac.calculator.service.allinterface.HumidifierService;
import ru.systemairac.calculator.service.allinterface.VaporDistributorService;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculationServiceImpl implements CalculationService {

    private final HumidifierService humidifierService;
    private final VaporDistributorService vaporDistributorService;

    public CalculationServiceImpl(HumidifierService humidifierService, VaporDistributorService vaporDistributorService) {
        this.humidifierService = humidifierService;
        this.vaporDistributorService = vaporDistributorService;
    }

    @Override
    public List<HumidifierDto> getHumidifiers(TechDataDto techDataDto) {
        return humidifierService.findDtoHumidifiers(techDataDto.getCalcCapacity(), techDataDto.getPhase(),
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
