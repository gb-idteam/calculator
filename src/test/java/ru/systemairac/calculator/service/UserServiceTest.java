package ru.systemairac.calculator.service;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.systemairac.calculator.FakeGenerator;
import ru.systemairac.calculator.domain.Role;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.service.allinterface.UserService;

import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static FakeGenerator fakeGenerator;
    private static Faker faker;
    private static PasswordEncoder encoder;

    @BeforeAll
    static void init() {
        fakeGenerator = new FakeGenerator();
        faker = new Faker(new Locale("ru"), new RandomService());
        encoder = new BCryptPasswordEncoder();
    }

    @BeforeEach
    void cleanUserRepository() {
        userRepository.deleteAll();
    }


    @Test
    void testSaveNewUserNoProjectsNoId() {
        User user = fakeGenerator.fakeUser();
        userService.save(user);
    }

    @Test
    void testSaveNewUserWithIdNoProjects() {
        User user = fakeGenerator.fakeUser();
        user.setId(123L);
        userService.save(user);
    }

    @Test
    void testSaveAndFindByNameIgnoreProjects() {
        User u = fakeGenerator.fakeUser();
        userService.save(u);

        User user = userService.findByEmail(u.getEmail()).orElseThrow();

        assertEquals(u.getEmail(), user.getEmail());
        assertEquals(u.getFullName(), user.getFullName());
        assertEquals(u.getPassword(), user.getPassword());
        assertEquals(u.getNameCompany(), user.getNameCompany());
        assertEquals(u.getPosition(), user.getPosition());
        assertEquals(u.getPhone(), user.getPhone());
//        assertEquals(u.getProjects(), user.getProjects());
    }

    @Test
    void testSaveUserDto() {
        UserDto userDto = fakeGenerator.fakeUserDto();
        userService.save(userDto);
    }

    @Test
    void testSaveUserDtoAndFindByName() {
        UserDto userDto = fakeGenerator.fakeUserDto();
        userService.save(userDto);
        userDto.setPassword(userDto.getMatchingPassword()); // так как UserService.save(UserDto) изменяет поле password в UserDto

        User user = userService.findByEmail(userDto.getEmail()).orElseThrow();


        assertFieldsEqual(user, userDto);
    }

    private void assertFieldsEqual(User user, UserDto userDto) {
        assertTrue(BCrypt.checkpw(userDto.getPassword(), user.getPassword()));
        assertEquals(user.getFullName(), userDto.getFullName());
        assertEquals(user.getNameCompany(), userDto.getNameCompany());
        assertEquals(user.getAddressCompany(), userDto.getAddressCompany());
        assertEquals(user.getPosition(), userDto.getPosition());
        assertEquals(user.getPhone(), userDto.getPhone());
        assertEquals(user.getEmail(), userDto.getEmail());
        if (user.getProjects() != null && userDto.getProjects() != null) {
            assertEquals(user.getProjects(), userDto.getProjects());
        } else {
            assertTrue(user.getProjects() == null || user.getProjects().isEmpty());
            assertTrue(userDto.getProjects() == null || userDto.getProjects().isEmpty());
        }
    }

    @Test
    void testSaveUserDtoPasswordMismatch() {
        UserDto userDto = fakeGenerator.fakeUserDto();
        String pw1 = faker.regexify("[A-Za-z0-9]{8,20}");
        String pw2;
        do {
            pw2 = faker.regexify("[A-Za-z0-9]{8,20}");
        } while (pw2.equals(pw1));
        userDto.setPassword(pw1);
        userDto.setMatchingPassword(pw2);

        assertThrows(RuntimeException.class, () ->
            userService.save(userDto)
        );
    }

    @Test
    void testDeleteWhenUserExists() {
        User user = fakeGenerator.fakeUser();
        userService.save(user);

        long id = userService.findByEmail(user.getEmail()).orElseThrow().getId();
        userService.delete(id);

        assertTrue(userService.findByEmail(user.getEmail()).isEmpty());
    }

    @Test
    void testDeleteWhenUserNotExists() {
        long id = new Random().nextLong();
        assertThrows(EmptyResultDataAccessException.class, () ->
            userService.delete(id)
        );
    }

    @Test
    void testGetByIdWhenUserExists() {
        User user = fakeGenerator.fakeUser();
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
        User user = fakeGenerator.fakeUser();
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

    @Test
    void testLoadUserByUsernameFail() {
        User user = fakeGenerator.fakeUser();
        userService.save(user);
        String email;
        do {
            email = faker.internet().emailAddress();
        } while (email.equals(user.getEmail()));
        final String finalEmail = email;
        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(finalEmail)
        );
    }

    @Disabled // нужно разобраться с шифрованием пароля
    @Test
    void getDtoById() {
        User user = fakeGenerator.fakeUser();
        long id = userRepository.save(user).getId();
        UserDto userDto = userService.getDtoById(id).orElseThrow();
        assertFieldsEqual(user, userDto);
    }

    @Disabled
    @Test
    void getByEmail() {
        User user = fakeGenerator.fakeUser();
        String email = userRepository.save(user).getEmail();
        UserDto userDto = userService.getByEmail(email);
        assertFieldsEqual(user, userDto);
    }

}
