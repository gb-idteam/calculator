package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.myenum.TypeCylinder;
import ru.systemairac.calculator.myenum.TypeWater;

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
    private int voltage;
    private int phase=1;
    private int length;
    private int width;
    private double calcCapacity;
}
