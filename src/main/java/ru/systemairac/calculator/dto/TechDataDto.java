package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.myenum.TypeMontage;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechDataDto implements Serializable {
    private Long id;
    private Integer airFlow = 1000;
    @NumberFormat(pattern = "###")
    private Integer altitude = 0;
    @NumberFormat(pattern = "###,###")
    private Double atmospherePressure = 101.325;
    private Double tempIn = 20.0;
    private Double humIn = 0.2;
    private Double humOut = 60.0;
    private EnumHumidifierType enumHumidifierType;
    private TypeMontage typeMontage;
    private EnumVoltageType voltage;
    private int height = 300;
    private int width = 500;
    private double calcCapacity;
}
