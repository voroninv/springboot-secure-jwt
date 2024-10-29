package com.base.jwt.services;

import com.base.jwt.models.User;
import com.base.jwt.models.UserDto;
import com.base.jwt.repositories.RoleRepository;
import com.base.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
    }

    @Transactional
    public User save(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        user.setRoles(userDto.getRoles().stream()
                .map(role -> Optional.ofNullable(roleRepository.findByName(role.getName())).orElse(role))
                .collect(Collectors.toSet()));

        return userRepository.save(user);
    }

    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}