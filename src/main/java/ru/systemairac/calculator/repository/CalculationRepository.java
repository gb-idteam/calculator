package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.Calculation;
@Repository
public interface CalculationRepository extends CrudRepository<Calculation,Long> {
}
