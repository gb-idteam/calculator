package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estimate")
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    Humidifier humidifier;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "estimate_humidifier_components",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "humidifier_components_id"))
    List<HumidifierComponent> humidifierComponents;

    @ManyToOne(cascade = CascadeType.MERGE)
    VaporDistributor vaporDistributor;

}
