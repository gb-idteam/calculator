package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.myenum.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechDataDto {
    private Long id;
    private Integer airFlow;
    private Integer altitude;
    private Double atmospherePressure;
    private Double tempIn;
    private Double humIn;
    private Double humOut;
    private EnumHumidifierType enumHumidifierType;
    private TypeMontage typeMontage;
    private EnumVoltageType voltage;
    private Integer length;
    private Integer width;
    private double calcCapacity;
}
