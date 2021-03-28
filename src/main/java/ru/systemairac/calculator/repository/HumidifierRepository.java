package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.myenum.EnumHumidifierType;

import java.util.List;

public interface HumidifierRepository extends JpaRepository<Humidifier,Long> {
    Humidifier findFirstById(Long id);
    List<Humidifier> findDistinctFirst3ByCapacityGreaterThanEqualAndHumidifierTypeEqualsAndPhaseOrderByCapacity(double capacity, EnumHumidifierType type, int phase);
    List<Humidifier> findDistinctFirst3ByCapacityGreaterThanEqualAndPhaseOrderByCapacity(double capacity, int phase);
}

