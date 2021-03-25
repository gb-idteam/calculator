package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "unit")
public class Unit {
    private static final String SEQ_NAME = "unit_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private UnitType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "calculation_id")
    private Calculation calculation;

    @Column
    private Long idSubTable;

}
