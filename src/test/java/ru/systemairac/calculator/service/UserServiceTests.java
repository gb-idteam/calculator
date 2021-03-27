package ru.systemairac.calculator.service;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.systemairac.calculator.domain.Role;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.repository.UserRepository;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static Faker faker;
    private static PasswordEncoder encoder;

    @BeforeAll
    static void init() {
        faker = new Faker(new Locale("ru"), new RandomService());
        encoder = new BCryptPasswordEncoder();
    }

    @BeforeEach
    void cleanUserRepository() {
        userRepository.deleteAll();
    }

    private User generateGoodUser() {
        User user = new User();
//        user.setId(null);
        user.setEmail(faker.bothify("?#?#?#?#?#@example.com"));
        user.setFullName(faker.name().fullName());
        user.setPassword(encoder.encode(faker.regexify("[A-Za-z0-9]{8,20}")));
        user.setNameCompany(faker.company().name());
        user.setPost(faker.address().fullAddress());
        user.setPhone(74951234567L);
//        user.setRole()
        user.setProjects(new ArrayList<>());
        return user;
    }

    private UserDto generateGoodUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail(faker.bothify("?#?#?#?#@example.com"));
        String pw = faker.regexify("[A-Za-z0-9]{8,20}");
        userDto.setPassword(pw);
        userDto.setMatchingPassword(pw);

        return userDto;
    }

    @Test
    void testSaveNewUserNoProjectsNoId() {
        User user = generateGoodUser();
        userService.save(user);
    }

    @Test
    void testSaveNewUserWithIdNoProjects() {
        User user = generateGoodUser();
        user.setId(123L);
        userService.save(user);
    }

    @Test
    void testSaveAndFindByNameIgnoreProjects() {
        User u = generateGoodUser();
        userService.save(u);

        User user = userService.findByEmail(u.getEmail()).orElseThrow();

        assertEquals(u.getEmail(), user.getEmail());
        assertEquals(u.getFullName(), user.getFullName());
        assertEquals(u.getPassword(), user.getPassword());
        assertEquals(u.getNameCompany(), user.getNameCompany());
        assertEquals(u.getPost(), user.getPost());
        assertEquals(u.getPhone(), user.getPhone());
//        assertEquals(u.getProjects(), user.getProjects());
    }

    @Test
    void testSaveUserDto() {
        UserDto userDto = generateGoodUserDto();
        userService.save(userDto);
    }

    @Test
    void testSaveUserDtoAndFindByName() {
        UserDto userDto = generateGoodUserDto();
        userService.save(userDto);

        User user = userService.findByEmail(userDto.getEmail()).orElseThrow();

        assertEquals(userDto.getEmail(), user.getEmail());
        assertTrue(BCrypt.checkpw(userDto.getPassword(), user.getPassword()));
        assertEquals(userDto.getEmail(), user.getEmail());
    }

    @Test
    void testSaveUserDtoPasswordMismatch() {
        UserDto userDto = generateGoodUserDto();
        String pw1 = faker.regexify("[A-Za-z0-9]{8,20}");
        String pw2;
        do {
            pw2 = faker.regexify("[A-Za-z0-9]{8,20}");
        } while (pw2.equals(pw1));
        userDto.setPassword(pw1);
        userDto.setMatchingPassword(pw2);

        assertThrows(RuntimeException.class, () -> {
            userService.save(userDto);
        });
    }

    @Test
    void testDeleteWhenUserExists() {
        User user = generateGoodUser();
        userService.save(user);

        long id = userService.findByEmail(user.getEmail()).orElseThrow().getId();
        userService.delete(id);

        assertTrue(userService.findByEmail(user.getEmail()).isEmpty());
    }

    @Test
    void testDeleteWhenUserNotExists() {
        long id = new Random().nextLong();
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userService.delete(id);
        });
    }

    @Test
    void testGetByIdWhenUserExists() {
        User user = generateGoodUser();
        userService.save(user);
        long id = userService.findByEmail(user.getEmail()).orElseThrow().getId();
        userService.getById(id);
    }

    @Test
    void testGetByIdWhenUserNotExists() {
        long id = new Random().nextLong();
        assertTrue(userService.getById(id).isEmpty());
    }

    @Test
    void testLoadUserByUsername() {
        User user = generateGoodUser();
        userService.save(user);
        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        String[] rolesActual = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
        String[] rolesExpected;
        if (user.getRoles() == null) {
            rolesExpected = new String[0];
        } else {
            rolesExpected = user.getRoles().stream()
                    .map(Role::getRoleName)
                    .toArray(String[]::new);
        }
        assertArrayEquals(rolesExpected, rolesActual);
    }

}
