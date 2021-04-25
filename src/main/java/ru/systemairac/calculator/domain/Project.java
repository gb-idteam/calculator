package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String title;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "project_id")
    private List<Calculation> calculations;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", title='" + title + '\'' +
                ", calculations=" + calculations +
                '}';
    }
}
