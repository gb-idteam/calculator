package ru.systemairac.calculator.service;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeMontage;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class HumidifierServiceImpl implements HumidifierService {

    @Override
    public List<HumidifierDto> findHumidifiers(Double power, int phase, EnumHumidifierType humidifierType, TypeMontage typeMontage) {
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

    @Override
    public HumidifierDto findById(Long id) {
        return HumidifierDto.builder()
                .id(1L)
                .articleNumber("1")
                .electricPower(5)
                .maxVaporOutput(20)
                .phase(3)
                .vaporPipeDiameter(25)
                .numberOfCylinders(1)
                .voltage(380)
                .price(BigDecimal.valueOf(1000))
                .build();
    }

    @Override
    public List<HumidifierDto> getAll() {
        return null;
    }

    @Override
    public void save(HumidifierDto humidifierDto) {
    }

    @Override
    public void deleteById(Long id) {
    }
}
