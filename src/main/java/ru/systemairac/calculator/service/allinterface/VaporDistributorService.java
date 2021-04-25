package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.VaporDistributorDto;

import java.util.HashMap;
import java.util.List;

public interface VaporDistributorService {
    void delete(Long id);
    void save(VaporDistributor vaporDistributor);
    HashMap<Long, VaporDistributorDto> getMapDistributorsByIds(int width, HashMap<Long, Integer> diameters, List<Humidifier> humidifiers);
}
