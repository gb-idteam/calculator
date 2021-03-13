package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private static final String SEQ_NAME = "unit_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    private String articleNumber;
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "humidifier_id")
    private Unit unit;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Option> options;

}
