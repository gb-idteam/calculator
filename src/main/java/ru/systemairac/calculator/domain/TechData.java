package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.myenum.TypeMontage;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tech_data")
public class TechData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Calculation calculation;

    private int airFlow;

    private double tempIn;

    @NumberFormat(pattern = "###")
    private Integer altitude;
    @NumberFormat(pattern = "###,###")
    private Double atmospherePressure;

    private double humIn;

    private double humOut;

    @Enumerated(EnumType.STRING)
    private EnumHumidifierType enumHumidifierType;

    @Enumerated(EnumType.STRING)
    private TypeMontage typeMontage;

    @Enumerated(EnumType.STRING)
    private EnumVoltageType voltage;

    private int height;

    private int width;

    private double calcCapacity;

    @UpdateTimestamp
    private LocalDateTime date;

    @Override
    public String toString() {
        return "TechData{" +
                "id=" + id +
                ", airFlow=" + airFlow +
                ", tempIn=" + tempIn +
                ", humIn=" + humIn +
                ", humOut=" + humOut +
                ", enumHumidifierType=" + enumHumidifierType +
                ", typeMontage=" + typeMontage +
                ", voltage=" + voltage +
                ", length=" + height +
                ", width=" + width +
                ", calcCapacity=" + calcCapacity +
                ", date=" + date +
                '}';
    }
}
