package org.rockend.service;

import org.rockend.entity.User;
import org.rockend.entity.UserRole;
import org.rockend.entity.dto.UserRegisterDto;
import org.rockend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public void save(UserRegisterDto userRegisterDto, UserRole role) {
//        User user = new User();
//        user.setName(userRegisterDto.getName());
//        user.setEmail(userRegisterDto.getEmail());
//        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
//        user.setRole(role);
//        userRepository.save(user);
//    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email = " + email + " not found"));
    }

    @Override
    public List<User> findAllByRoleIn(Collection<UserRole> roles) {
        return userRepository.findAllByRoleInOrderById(roles);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateRole(int id, UserRole newRole) {
        userRepository.updateRole(id, newRole);
    }
}
