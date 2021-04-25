package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HumidifierDto implements Serializable {
    private Long id;
    private String title;
    private Image image;
    private String articleNumber;
    private Double electricPower;
    private Double capacity;
    private EnumVoltageType voltage;
    private Integer numberOfCylinders;
    private Integer vaporPipeDiameter;
    private BigDecimal price;

    private EnumHumidifierType humidifierType;
}
