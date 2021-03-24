package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.myEnum.HumidifierType;

public interface HumidifierRepository extends JpaRepository<Humidifier, Long> {

    Humidifier findFirstByMaxVaporOutput(float maxVaporOut);

    Humidifier findFirstByPhase(int phase);

    Humidifier findByHumidifierType(HumidifierType humidifierType);

}
