package ru.systemairac.calculator.domain.humidifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.myEnum.EnumHumidifierType;
import ru.systemairac.calculator.myEnum.TableName;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "humidifier_type")
public class HumidifierType {
    private static final String SEQ_NAME = "unit_types_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumHumidifierType tableName;

}
