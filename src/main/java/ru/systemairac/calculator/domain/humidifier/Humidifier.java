package ru.systemairac.calculator.domain.humidifier;

import lombok.*;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.domain.UnitType;
import ru.systemairac.calculator.myEnum.HumidifierType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_humidifiers")
public class Humidifier {

    private static final String SEQ_NAME = "humidifier_seq";

    /**
     * Идентификатор в таблице.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    /**
     * Тип установки, для того, чтобы таблица Calculation знала к какой таблице обращаться
     */
    private UnitType type = UnitType.HUMIDIFIER;
    /**
     * Номер артикуля. Должен быть уникален.
     */
    @Column(unique = true)
    private String articleNumber;

    /**
     * Бренд увлажнителя.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Brand brand;

    /**
     * Тип увлажнителя: ТЭНовый или электродный.
     */

    private HumidifierType humidifierType;

    /**
     * Максимальная потребляемая электрическая мощность, в кВт.
     */
    private float electricPower;

    /**
     * Максимальная производительность увлажнителя, в кг воды в час (кг/ч)
     */
    private float maxVaporOutput;

    /**
     * Число фаз (1 или 3).
     */
    private int phase;

    /**
     * Рабочее напряжение, в В.
     */
    private int voltage;

    /**
     * Количество цилиндров в увлажнителе.
     */
    private int numberOfCylinders;

    /**
     * Диаметр паропровода, в мм.
     */
    private int vaporPipeDiameter;


    // cascade для сущностей ниже не нужен

    /**
     * Парораспределители, подходящие для данной модели.
     */
    @ManyToMany
    @JoinTable(
            name = "humidifiers_vaporDistributors",
            joinColumns = @JoinColumn(name = "humidifier_id"),
            inverseJoinColumns = @JoinColumn(name = "vaporDistributor_id"))
    private List<VaporDistributor> vaporDistributors;

    @ManyToMany
    @JoinTable(
            name = "humidifiers_humidifierComponents",
            joinColumns = @JoinColumn(name = "humidifier_id"),
            inverseJoinColumns = @JoinColumn(name = "humidifierComponent_id"))
    private List<HumidifierComponent> humidifierComponents;

    private BigDecimal price;
}
