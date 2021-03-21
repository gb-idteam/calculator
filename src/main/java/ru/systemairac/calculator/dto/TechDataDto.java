package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String humidifierType;
    private String typeMontage;
    private String typeWater;
    private String typeCylinder;
    private int voltage;
    private int phase;
    private int length;
    private int width;
}
