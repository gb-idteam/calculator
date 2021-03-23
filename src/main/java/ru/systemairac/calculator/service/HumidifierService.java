package ru.systemairac.calculator.service;

import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.myEnum.HumidifierType;
import ru.systemairac.calculator.myEnum.TypeMontage;

import java.util.List;

public interface HumidifierService {

    List<HumidifierDto> findHumidifiers(Double power, int phase, HumidifierType humidifierType, TypeMontage typeMontage);

    HumidifierDto findById(Long id);

    List<HumidifierDto> getAll();

    void save(HumidifierDto humidifierDto);

    void deleteById(Long id);
}
