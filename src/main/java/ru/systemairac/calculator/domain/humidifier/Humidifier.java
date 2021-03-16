package ru.systemairac.calculator.domain.humidifier;

import lombok.*;
import ru.systemairac.calculator.domain.Unit;

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
    @ManyToOne(cascade = CascadeType.PERSIST)
    private HumidifierType humidifierType;

    // TODO: int, float, double, BigDecimal?

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
    private int phasicity;

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
    private float vaporPipeDiameter;

    /**
     * Доступные (много) для данной модели увлажнителя парораспределители.
     */
    @ManyToMany
    private List<VaporDistributor> vaporDistributors;

    /**
     * Доступный (один) для данной модели вентиляторный распределитель.
     */
    @ManyToOne
    private FanDistributor fanDistributor;

    /**
     * Доступная (одна) для данной модели панель master-slave.
     */
    @ManyToOne
    private MasterSlavePanel masterSlavePanel;

    /**
     * Доступный (один) для данной модели LED-дисплей.
     */
    @ManyToOne
    private LEDDisplay ledDisplay;

    // TODO: место для поля "кабель для LED-дисплея"
    // возможно, лучше отправить его в LEDDisplay

    /**
     * Доступный (один) для данной модели комплект охлаждения дренажа.
     */
    @ManyToOne
    private DrainageCoolingKit drainageCoolingKit;

    @ManyToOne
    private CylinderWaterHeatingKit cylinderWaterHeatingKit;

    @ManyToOne
    private ProtectiveCabinet protectiveCabinet;

    @ManyToOne
    private LeakSensor leakSensor;

    @ManyToOne
    private YAdapter yAdapter;

    // TODO: что делать с "неразб. цилиндр" и "разборн. цилиндр"?

    @ManyToMany
    private List<OtherDiameterAdapter> otherDiameterAdapters;

    // TODO: что такое "канальный"?

    @ManyToMany
    private List<RoomHumiditySensor> roomHumiditySensors;

    // TODO: трубки, возможно, лучше отдельно?

    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "humidifier_id")
    private Unit unit;

}
