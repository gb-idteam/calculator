package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "tbl_protective_cabinets")
public class ProtectiveCabinet extends ArticledEntity {
    // TODO: поле "класс защиты"?
}