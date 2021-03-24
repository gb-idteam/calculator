package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.myEnum.TypeMontage;
import ru.systemairac.calculator.myEnum.HumidifierType;
import ru.systemairac.calculator.myEnum.TypeCylinder;
import ru.systemairac.calculator.myEnum.TypeWater;

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
    private int length;
    private int width;
}
