package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.HumidifierComponentDto;
import ru.systemairac.calculator.dto.HumidifierDto;

import java.util.HashMap;
import java.util.List;

public interface HumidifierComponentService {
    List<HumidifierComponent> getAllComponent();

    HashMap<Long, List<HumidifierComponentDto>> getAllComponentByHumidifiers(List<HumidifierDto> humidifiers);

    HashMap<Long, List<HumidifierComponentDto>> findByHumidifier(HumidifierDto humidifierDto);

    HumidifierComponent findById(Long id);

    void save(HumidifierComponent humidifierComponent);

    void deleteById(Long id);
}
