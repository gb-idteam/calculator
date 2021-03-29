package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.Role;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.domain.User;

import java.util.List;

public interface TechDataRepository  extends CrudRepository<TechData, Long> {
    List<TechData> findByUser(User user);
}
