package ru.systemairac.calculator.domain.humidifier;

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
@Table(name = "tbl_humidifier_types")
public class HumidifierType {

    private static final String SEQ_NAME = "humidifier_type_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String typeName;

    @OneToMany
    private List<Humidifier> humidifiers;
}
