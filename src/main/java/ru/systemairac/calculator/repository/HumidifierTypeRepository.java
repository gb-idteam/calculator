package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.humidifier.HumidifierType;

@Repository
public interface HumidifierTypeRepository extends JpaRepository<HumidifierType,Long> {
}
