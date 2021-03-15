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
    @AfterEach
    void cleanTable() {
        userRepository.deleteAll();
    }

    private User generateGoodUser() {
        User user = new User();
//        user.setId(null);
        user.setName(faker.regexify("[A-Za-z0-9]{5,50}"));
        user.setFullName(faker.name().fullName());
        user.setPassword(encoder.encode(faker.regexify("[A-Za-z0-9]{8,20}")));
        user.setEmail(faker.bothify("?#?#?#?#?#@example.com"));
        user.setNameCompany(faker.company().name());
        user.setPost(faker.address().fullAddress());
        user.setPhone(74951234567L);
//        user.setRole()
        user.setProjects(new ArrayList<>());
        return user;
    }

    private UserDto generateGoodUserDto() {
        UserDto userDto = new UserDto();
        userDto.setName(faker.regexify("[A-Za-z0-9]{5,50}"));
        String pw = faker.regexify("[A-Za-z0-9]{8,20}");
        userDto.setPassword(pw);
        userDto.setMatchingPassword(pw);
        userDto.setEmail(faker.bothify("?#?#?#?#@example.com"));

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

        User user = userService.findByName(u.getName());

        assertEquals(u.getName(), user.getName());
        assertEquals(u.getFullName(), user.getFullName());
        assertEquals(u.getPassword(), user.getPassword());
        assertEquals(u.getEmail(), user.getEmail());
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

        User user = userService.findByName(userDto.getName());

        assertEquals(userDto.getName(), user.getName());
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

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.save(userDto);
        });
    }

    @Test
    void testDeleteWhenUserExists() {
        User user = generateGoodUser();
        userService.save(user);

        long id = userService.findByName(user.getName()).getId();
        userService.delete(id);

        assertNull(userService.findByName(user.getName()));
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
        long id = userService.findByName(user.getName()).getId();
        userService.getById(id);
    }

    @Test
    void testGetByIdWhenUserNotExists() {
        long id = new Random().nextLong();
        assertNull(userService.getById(id));
    }

    @Test
    void testLoadUserByUsername() {
        User user = generateGoodUser();
        userService.save(user);
        UserDetails userDetails = userService.loadUserByUsername(user.getName());
        assertEquals(user.getName(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        String[] rolesActual = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
        String[] rolesExpected = new String[] {user.getRole().name()};
        assertArrayEquals(rolesExpected, rolesActual);
    }

}
