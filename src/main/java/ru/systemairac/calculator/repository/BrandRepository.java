package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.domain.Role;

public interface BrandRepository  extends CrudRepository<Brand, Long> {
}
