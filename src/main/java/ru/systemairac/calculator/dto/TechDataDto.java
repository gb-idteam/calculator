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
    private int airFlow;
    private double tempIn;
    private double humIn;
    private double humOut;
    private EnumHumidifierType enumHumidifierType;
    private TypeMontage typeMontage;
    private TypeWater typeWater;
    private TypeCylinder typeCylinder;
    private EnumVoltageType voltage;
    private int length;
    private int width;
    private double calcCapacity;
}
