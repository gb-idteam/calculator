package ru.systemairac.calculator.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.UserDto;

public interface UserService extends UserDetailsService {
    boolean save(UserDto userDto);
    User findByName(String name);
    void delete(Long id);
    void save(User user);
    User getById(Long id);
    UserDto findById(Long id);
}
