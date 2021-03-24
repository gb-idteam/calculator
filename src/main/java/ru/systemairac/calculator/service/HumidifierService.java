package ru.systemairac.calculator.service;

import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.myEnum.HumidifierType;

public interface HumidifierService {

    Humidifier getByMaxVaporOut(float maxVaporOut);

    Humidifier getByPhase(int phase);

    Humidifier getByHumidifierType(HumidifierType humidifierType);

    boolean save (HumidifierDto humidifierDto);

    HumidifierDto findById(Long id);
}
