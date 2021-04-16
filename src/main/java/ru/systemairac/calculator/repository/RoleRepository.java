package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.systemairac.calculator.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Short> {

}