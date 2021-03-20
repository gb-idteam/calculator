package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.TypeMontage;
import ru.systemairac.calculator.domain.humidifier.HumidifierType;
import ru.systemairac.calculator.domain.humidifier.TypeCylinder;
import ru.systemairac.calculator.domain.humidifier.TypeWater;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechDataDto {
    private int airFlow;
    private double tempIn;
    private double tempOut;
    private double humIn;
    private double humOut;
    private HumidifierType humidifierType;
    private TypeMontage typeMontage;
    private TypeWater typeWater;
    private TypeCylinder typeCylinder;
    private int voltage;
    private int phase;
    private int lengthDuct;
    private int widthDuct;
}
