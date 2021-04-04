package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeCylinder;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.myenum.TypeWater;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tech_data")
public class TechData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    private int airFlow;

    private double tempIn;

    private double humIn;

    private double humOut;

    @Enumerated(EnumType.STRING)
    private EnumHumidifierType enumHumidifierType;

    @Enumerated(EnumType.STRING)
    private TypeMontage typeMontage;

    private int voltage;

    private int phase;

    private int length;

    private int width;

    private double calcCapacity;

    @UpdateTimestamp
    private LocalDateTime date;
}
