package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.myenum.HumidifierComponentType;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HumidifierComponentDto implements Serializable {
    private Long id;
    private Image image;
    private String articleNumber;
    private HumidifierComponentType type;
    private boolean optional;
    private BigDecimal price;
}
