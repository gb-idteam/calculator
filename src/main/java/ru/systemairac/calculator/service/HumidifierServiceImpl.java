package ru.systemairac.calculator.service;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.TypeMontage;
import ru.systemairac.calculator.domain.humidifier.HumidifierType;
import ru.systemairac.calculator.dto.HumidifierDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class HumidifierServiceImpl implements HumidifierService {

    @Override
    public List<HumidifierDto> findHumidifiers(Double power, int phase, HumidifierType humidifierType, TypeMontage typeMontage) {
        return Arrays.asList(HumidifierDto.builder()
                        .id(2L)
                        .articleNumber("123")
                        .electricPower(7)
                        .maxVaporOutput(25)
                        .phase(3)
                        .vaporPipeDiameter(25)
                        .numberOfCylinders(1)
                        .voltage(380)
                        .price(BigDecimal.valueOf(1500))
                        .build(),
                HumidifierDto.builder()
                        .id(1L)
                        .articleNumber("1")
                        .electricPower(5)
                        .maxVaporOutput(20)
                        .phase(3)
                        .vaporPipeDiameter(25)
                        .numberOfCylinders(1)
                        .voltage(380)
                        .price(BigDecimal.valueOf(1000))
                        .build());
    }
}
