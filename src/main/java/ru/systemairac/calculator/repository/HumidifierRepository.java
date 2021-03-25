package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.systemairac.calculator.domain.humidifier.Humidifier;

public interface HumidifierRepository extends JpaRepository<Humidifier,Long> {
}
