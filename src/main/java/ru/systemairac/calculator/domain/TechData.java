package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeCylinder;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.myenum.TypeWater;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tech_data")
public class TechData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int airFlow;

    private double tempIn;

    private double tempOut;

    private double humIn;

    private double humOut;

    @Enumerated(EnumType.STRING)
    private EnumHumidifierType enumHumidifierType;

    @Enumerated(EnumType.STRING)
    private TypeMontage typeMontage;

    @Enumerated(EnumType.STRING)
    private TypeWater typeWater;

    @Enumerated(EnumType.STRING)
    private TypeCylinder typeCylinder;

    private int voltage;

    private int phase;

    private int length;

    private int width;

    private double calcCapacity;
}
