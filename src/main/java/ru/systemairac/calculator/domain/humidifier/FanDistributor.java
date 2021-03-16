package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
// @AllArgsConstructor
@Entity
@Table(name = "tbl_fan_distributors")
public class FanDistributor extends ArticledEntity {

}
