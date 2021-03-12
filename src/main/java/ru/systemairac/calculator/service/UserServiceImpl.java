package ru.systemairac.calculator.service;

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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper = UserMapper.MAPPER;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean save(UserDto userDto) {
        if(!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())){
            throw new RuntimeException ("Password is not equal");
        }
        User user = User.builder()
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    public UserDto findById(Long id) {
        return mapper.fromUser(userRepository.getOne (id));
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if(user == null){
            throw new UsernameNotFoundException ("User not found with name: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<> ();
        roles.add(new SimpleGrantedAuthority (user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles);
    }
}
