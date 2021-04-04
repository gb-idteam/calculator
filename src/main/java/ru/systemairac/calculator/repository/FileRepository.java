package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.FileEntity;

public interface FileRepository extends CrudRepository<FileEntity, Long> {
}
