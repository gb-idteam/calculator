package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.humidifier.Humidifier;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstimateDto {
    private Humidifier humidifier;
    private List<HumidifierComponentDto> humidifierComponentDtoList;
    private VaporDistributorDto vaporDistributorDto;
    private String sum;
}
