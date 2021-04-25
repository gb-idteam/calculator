package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.dto.EstimateDto;

public interface EstimateService {
    EstimateDto save(Long calculationId, Long idHumidifier,Long[] idSelectedOptions, Long idDistributor);
}
