package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Short> {

}
