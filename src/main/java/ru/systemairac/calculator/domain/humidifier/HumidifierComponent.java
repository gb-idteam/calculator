package ru.systemairac.calculator.domain.humidifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.myenum.ConverterHumidifierComponentType;
import ru.systemairac.calculator.myenum.HumidifierComponentType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "humidifier_component")
@Table(name = "humidifier_component")
public class HumidifierComponent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;

    @Column(unique = true)
    private String articleNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Image image;

    @Convert(converter = ConverterHumidifierComponentType.class)
    private HumidifierComponentType type;

    private boolean optional;

    @Column
    private BigDecimal price;

}
