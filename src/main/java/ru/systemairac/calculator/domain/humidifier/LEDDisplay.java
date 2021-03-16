package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
// @AllArgsConstructor
@Entity
@Table(name = "tbl_led_displays")
public class LEDDisplay extends ArticledEntity {

}
