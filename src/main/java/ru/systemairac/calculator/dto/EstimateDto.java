package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstimateDto implements Serializable {
    private Humidifier humidifier;
    private List<HumidifierComponent> humidifierComponents;
    private VaporDistributor vaporDistributor;
}
