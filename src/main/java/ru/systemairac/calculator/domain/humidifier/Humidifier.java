package ru.systemairac.calculator.domain.humidifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "humidifier")
public class Humidifier implements Serializable {

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
     * Ссылка на картинку
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Image image;

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
            name = "humidifiers_vapor_distributors",
            joinColumns = @JoinColumn(name = "humidifier_id"),
            inverseJoinColumns = @JoinColumn(name = "vapor_distributor_id"))
    private List<VaporDistributor> vaporDistributors;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "humidifiers_humidifier_components",
            joinColumns = @JoinColumn(name = "humidifier_id"),
            inverseJoinColumns = @JoinColumn(name = "humidifier_component_id"))
    private List<HumidifierComponent> humidifierComponents;

    @Column
    private BigDecimal price;
}
