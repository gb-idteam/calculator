package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "calculation")
public class Calculation {
    private static final String SEQ_NAME = "calculation_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Unit> unit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Project project;

    @UpdateTimestamp
    private LocalDateTime date;
}
