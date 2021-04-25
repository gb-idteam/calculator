package ru.systemairac.calculator.service.allinterface;

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
    UserDto getByEmail(String email);
    boolean existsByEmail(String email);
    void userConfirmation(User user, String confirmation);
}
