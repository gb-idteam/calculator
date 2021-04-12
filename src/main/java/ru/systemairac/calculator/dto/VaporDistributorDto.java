package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Image;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaporDistributorDto {
    private Long id;
    private Image image;
    private String articleNumber;
    private int length;
    private int diameter;
    private String title = "Парораспределитель из нержавеющей стали, d=" + this.getDiameter() + "мм, L=" + this.getLength() + "мм";
    private BigDecimal price;
}
