package ru.systemairac.calculator.domain.humidifier;

import lombok.*;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.myEnum.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "humidifiers")
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
    @Transient
    private TableName type = TableName.HUMIDIFIER;
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
    @OneToOne(cascade = CascadeType.PERSIST)
    private HumidifierType humidifierType;

    /**
     * Максимальная потребляемая электрическая мощность, в кВт.
     */
    @Column
    private float electricPower;

    /**
     * Максимальная производительность увлажнителя, в кг воды в час (кг/ч)
     */
    @Column
    private float capacity;

    /**
     * Число фаз (1 или 3).
     */
    @Column
    private int phase;

    /**
     * Рабочее напряжение, в В.
     */
    @Column
    private int voltage;

    /**
     * Количество цилиндров в увлажнителе.
     */
    @Column
    private int numberOfCylinders;

    /**
     * Диаметр паропровода, в мм.
     */
    @Column
    private int vaporPipeDiameter;


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

    @Column
    private BigDecimal price;
}
