package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_other_diameter_adapters")
public class OtherDiameterAdapter extends ArticledEntity {

    // TODO: int, float, double, BigDecimal?

    private float bigDiameter;

    private float smallDiameter;
}