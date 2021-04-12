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
    private String title;
    private int length;
    private int diameter;
    private BigDecimal price;

    public VaporDistributorDto(Long id, Image image, String articleNumber, int length, int diameter, BigDecimal price) {
        this.id = id;
        this.image = image;
        this.articleNumber = articleNumber;
        this.title = "Парораспределитель из нержавеющей стали, d=" + this.diameter + "мм, L=" + this.length + "мм";;
        this.length = length;
        this.diameter = diameter;
        this.price = price;
    }
}
