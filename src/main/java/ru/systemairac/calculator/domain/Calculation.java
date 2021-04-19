package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "calculation")
public class Calculation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "calculation_id")
    private List<TechData> techData;

    @OneToOne(cascade = CascadeType.ALL)
    private Estimate estimate;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Project project;

    @UpdateTimestamp
    private LocalDateTime date;

    @Override
    public String toString() {
        return "Calculation{" +
                "id=" + id +
                ", techData=" + techData +
                ", date=" + date +
                '}';
    }
}
