package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_vapor_distributors")
public class VaporDistributor {

    private static final String SEQ_NAME = "vapor_distributor_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String articleNumber;

    // TODO: int, float, double, BigDecimal?

    private int diameter;

    private int length;

}
