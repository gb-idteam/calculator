package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.HumidifierComponentDto;

import java.util.List;
@Repository
public interface HumidifierComponentRepository extends JpaRepository<HumidifierComponent, Long>{
    List<HumidifierComponent> findAllById(Iterable<Long> ids);
}

