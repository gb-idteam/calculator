package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.systemairac.calculator.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
}
