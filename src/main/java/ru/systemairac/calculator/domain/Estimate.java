package ru.systemairac.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estimate")
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
