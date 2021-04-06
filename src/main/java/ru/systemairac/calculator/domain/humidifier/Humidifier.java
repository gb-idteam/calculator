package ru.systemairac.calculator.domain.humidifier;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "humidifier")
public class Humidifier {

    /**
     * Идентификатор в таблице.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Номер артикуля. Должен быть уникален.
     */
    @Column(unique = true)
    private String articleNumber;

    /**
     * Модель увлажнителя
     */
    @Column
    private String title;

    /**
     * Бренд увлажнителя.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Brand brand;

    /**
     * Тип увлажнителя: ТЭНовый или электродный.
     */
    @Enumerated(EnumType.STRING)
    private EnumHumidifierType humidifierType;

    /**
     * Максимальная потребляемая электрическая мощность, в кВт.
     */
    @Column
    private Double electricPower;

    /**
     * Максимальная производительность увлажнителя, в кг воды в час (кг/ч)
     */
    @Column
    private Double capacity;

    /**
     * Тип сети (1~220 или 3~380).
     */
    @Enumerated(EnumType.STRING)
    private EnumVoltageType voltage;

    /**
     * Количество цилиндров в увлажнителе.
     */
    @Column
    private Integer numberOfCylinders;

    /**
     * Диаметр паропровода, в мм.
     */
    @Column
    private Integer vaporPipeDiameter;


    /**
     * Парораспределители, подходящие для данной модели.
     */
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "humidifiers_vaporDistributors",
            joinColumns = @JoinColumn(name = "humidifier_id"),
            inverseJoinColumns = @JoinColumn(name = "vaporDistributor_id"))
    private List<VaporDistributor> vaporDistributors;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "humidifiers_humidifierComponents",
            joinColumns = @JoinColumn(name = "humidifier_id"),
            inverseJoinColumns = @JoinColumn(name = "humidifierComponent_id"))
    private List<HumidifierComponent> humidifierComponents;

    @Column
    private BigDecimal price;
}
