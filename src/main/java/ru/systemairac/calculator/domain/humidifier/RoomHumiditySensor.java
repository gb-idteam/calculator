package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "tbl_room_humidity_sensors")
public class RoomHumiditySensor extends ArticledEntity {

    // TODO: тип комнатного датчика
}