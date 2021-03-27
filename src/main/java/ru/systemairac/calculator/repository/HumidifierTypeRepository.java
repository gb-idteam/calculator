package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.systemairac.calculator.domain.humidifier.HumidifierType;

public interface HumidifierTypeRepository extends JpaRepository<HumidifierType,Long> {
}
