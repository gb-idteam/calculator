package ru.systemairac.calculator.domain.humidifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.myenum.HumidifierComponentType;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "humidifier_component")
public class HumidifierComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;

    @Column(unique = true)
    private String articleNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Image image;

    @Enumerated(EnumType.STRING)
    private HumidifierComponentType type;

    private boolean optional;

    @Column
    private BigDecimal price;

}
