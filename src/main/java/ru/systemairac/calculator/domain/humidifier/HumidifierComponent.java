package ru.systemairac.calculator.domain.humidifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.myEnum.HumidifierComponentType;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_humidifier_components")
public class HumidifierComponent {

    private static final String SEQ_NAME = "humidifier_component_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;

    @Column(unique = true)
    private String articleNumber;

    @Enumerated(EnumType.STRING)
    private HumidifierComponentType type;

    private boolean optional;

}
