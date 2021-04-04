package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.FileEntity;

import java.util.Optional;

public interface FileRepository extends CrudRepository<FileEntity, Long> {
    Optional<FileEntity> findByName(String fileName);
}
