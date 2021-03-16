package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
// @AllArgsConstructor
@Entity
@Table(name = "tbl_cylinder_casings")
public class CylinderCasing extends ArticledEntity {

}