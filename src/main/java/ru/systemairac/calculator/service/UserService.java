package ru.systemairac.calculator.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.UserDto;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    boolean save(UserDto userDto);
    Optional<User> findByEmail(String email);
    void delete(Long id);
    void save(User user);
    Optional<User> getById(Long id);
    Optional<UserDto> getDtoById(Long id);
    User getByEmail(String name);
}
