package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.myEnum.HumidifierType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HumidifierDto {
    private Long id;
    private String articleNumber;
    private float electricPower;
    private float maxVaporOutput;
    private HumidifierType humidifierType;
    private int phase;
    private int voltage;
    private int numberOfCylinders;
    private int vaporPipeDiameter;
    private BigDecimal price;
    private List<Project> projects;
}
