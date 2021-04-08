package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.Estimate;

public interface EstimateRepository extends CrudRepository<Estimate,Long> {
}
