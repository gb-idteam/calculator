package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
// @AllArgsConstructor
@Entity
@Table(name = "tbl_cylinder_water_heating_kits")
public class CylinderWaterHeatingKit extends ArticledEntity {

}