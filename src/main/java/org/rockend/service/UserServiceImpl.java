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

    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email = " + email + " not found"));
    }
}
