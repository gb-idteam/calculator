package ru.systemairac.calculator.repository.humidifier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.myenum.EnumHumidifierType;

import java.util.List;

public interface HumidifierRepository extends JpaRepository<Humidifier, Long>, JpaSpecificationExecutor<Humidifier> {
    List<Humidifier> findAllById(List<Long> ids);

    Humidifier findHumidifierById(Long id);
}

