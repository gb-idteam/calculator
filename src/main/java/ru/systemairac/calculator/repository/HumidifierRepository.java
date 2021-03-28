package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.myenum.EnumHumidifierType;

import java.util.List;

public interface HumidifierRepository extends JpaRepository<Humidifier,Long>, JpaSpecificationExecutor<Humidifier> {
    Humidifier findFirstById(Long id);
    List<Humidifier> findDistinctFirst3ByCapacityGreaterThanEqualAndHumidifierType_TypeLikeAndPhaseOrderByCapacity(double capacity, EnumHumidifierType humidifierType_type, int phase);
    List<Humidifier> findDistinctFirst3ByCapacityGreaterThanEqualAndPhaseOrderByCapacity(double capacity, int phase);
}

