package ru.systemairac.calculator.service;

import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;

import java.util.List;

public interface HumidifierComponentService {
    List<HumidifierComponent> getAllComponent();

    HumidifierComponent findById(Long id);

    void save(HumidifierComponent humidifierComponent);

    void deleteById(Long id);
}
