package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import ru.systemairac.calculator.myenum.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechDataDto implements Serializable {
    private Long id;
    private Integer airFlow;
    @NumberFormat(pattern = "###")
    private Integer altitude = 0;
    @NumberFormat(pattern = "###,###")
    private Double atmospherePressure = 101.325;
    private Double tempIn;
    private Double humIn;
    private Double humOut;
    private EnumHumidifierType enumHumidifierType;
    private TypeMontage typeMontage;
    private EnumVoltageType voltage;
    private int length;
    private int width;
    private double calcCapacity;
}
