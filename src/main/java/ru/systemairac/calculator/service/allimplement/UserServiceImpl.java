package ru.systemairac.calculator.service.allimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.mapper.UserMapper;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.service.allinterface.MailService;
import ru.systemairac.calculator.service.allinterface.UserService;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper = UserMapper.MAPPER;

    @Autowired
    public void setMailService(MailService mailService){
        this.mailService = mailService;
    }

    public UserServiceImpl( UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean save(UserDto userDto) {
        if(!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())){
            throw new RuntimeException ("Password is not equal");
        }
        createNewUser(mapper.toUser(userDto));
        return true;
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

    @Override
    public Optional<UserDto> getDtoById(Long id) {
        Optional<User> u = getById(id);
        return u.map(mapper::fromUser);
    }

    @Override
    public UserDto getByEmail(String email) {
        return mapper.fromUser(userRepository.findByEmail(email));
    }

    @Override
    public void delete(Long id){
            userRepository.deleteById (id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email).orElseThrow( () ->
                new UsernameNotFoundException("User not found with email: " + email)
        );

        List<GrantedAuthority> authorityList =
            user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorityList);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void generateKey(User user) {
        Random random = new Random();
        user.setConfirmKeys(
                String.format ("%04d",
                        random.ints(0, 9999)
                                .findFirst()
                                .getAsInt()));
        userRepository.save(user);
    }
    @Override
    public void userConfirmation(User user, String confirmation){
        if (user.getConfirmKeys().equals(confirmation)){
            user.setConfirmed(true);
            userRepository.save(user);
        } else try {
            throw new ValidationException("User cannot verification!");
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public boolean createNewUser(User user){
        if (userRepository.findByEmail(user.getEmail()) != null){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        generateKey(user);
        Runnable mailTask = () -> {
            mailService.sendConfirmationMail(user.getConfirmKeys(), user);
        };
        Thread thread = new Thread(mailTask);
        thread.start();
        return true;
    }
}
