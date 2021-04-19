package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.dto.TechDataDto;

import java.util.Optional;

public interface TechDataService {

    Long save(TechDataDto dto, Long idCalc);

    Optional<TechData> getById(long id);
    TechDataDto findById(long id);
}
