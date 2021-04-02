package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.myenum.HumidifierComponentType;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HumidifierComponentDto {
    private Long id;
    private String articleNumber;
    private HumidifierComponentType type;
    private boolean optional;
    private BigDecimal price;
}
