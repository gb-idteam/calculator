package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.Image;

public interface ImageRepository extends CrudRepository<Image,Long> {
}
