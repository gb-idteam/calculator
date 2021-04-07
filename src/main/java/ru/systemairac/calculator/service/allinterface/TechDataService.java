package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.TechDataDto;

import java.util.List;
import java.util.Optional;

public interface TechDataService {

    void save(TechDataDto dto, Long idCalc);

    Optional<TechData> getById(long id);
}
