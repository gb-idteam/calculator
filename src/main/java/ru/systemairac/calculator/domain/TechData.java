package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import ru.systemairac.calculator.myenum.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Calculation calculation;

    private int airFlow;

    private double tempIn;

    private double humIn;

    private double humOut;

    @Enumerated(EnumType.STRING)
    private EnumHumidifierType enumHumidifierType;

    @Enumerated(EnumType.STRING)
    private TypeMontage typeMontage;

    @Enumerated(EnumType.STRING)
    private EnumVoltageType voltage;

    private int length;

    private int width;

    private double calcCapacity;

    @UpdateTimestamp
    private LocalDateTime date;
}
