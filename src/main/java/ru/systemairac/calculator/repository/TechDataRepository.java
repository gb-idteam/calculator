package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.TechData;
@Repository
public interface TechDataRepository  extends CrudRepository<TechData, Long> {
}
