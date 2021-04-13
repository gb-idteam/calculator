package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;

import java.util.List;

@Repository
public interface HumidifierComponentRepository extends JpaRepository<HumidifierComponent, Long> {
    @SuppressWarnings("NullableProblems")
    List<HumidifierComponent> findAllById(Iterable<Long> ids);
}

