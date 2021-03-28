package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HumidifierDto {
    private Long id;
    private String articleNumber;
    private double electricPower;
    private double capacity;
    private int phase;
    private int voltage;
    private int numberOfCylinders;
    private int vaporPipeDiameter;
    private BigDecimal price;
}
