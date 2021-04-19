package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.Brand;

public interface BrandRepository  extends CrudRepository<Brand, Long> {
}
