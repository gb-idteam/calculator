package ru.systemairac.calculator.service.allimplement;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Role;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.mapper.UserMapper;
import ru.systemairac.calculator.myenum.RoleName;
import ru.systemairac.calculator.repository.RoleRepository;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.service.allinterface.UserService;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper = UserMapper.MAPPER;

    private List<Role> roles;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void init() {
        roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        if (roles.size() == 0) {
            // initialize roles
            short id = 1;
            for (RoleName roleName : RoleName.values()) {
                roleRepository.save(new Role(id++, roleName.name()));
            }
        } else {
            // TODO: verify roles in db
        }
    }

    @Override
    @Transactional
    public boolean save(UserDto userDto) {
        if(!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())){
            throw new RuntimeException ("Password is not equal");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(mapper.toUser(userDto));
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
}
