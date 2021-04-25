package ru.systemairac.calculator.repository.humidifier;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.humidifier.Humidifier;

import java.util.List;

@Repository
public interface HumidifierRepository extends CrudRepository<Humidifier, Long>, JpaSpecificationExecutor<Humidifier> {
    List<Humidifier> findAllById(Iterable<Long> ids);
    Humidifier findHumidifierById(Long id);
}

