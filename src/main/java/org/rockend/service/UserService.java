package org.rockend.service;

import org.rockend.entity.User;
import org.rockend.entity.UserRole;
import org.rockend.entity.dto.UserRegisterDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {
//    void save(UserRegisterDto userRegisterDto, UserRole role);
    void save(User user);

    public User getCurrentUser();

    public List<User> findAllByRoleIn(Collection<UserRole> roles);

    void deleteById(int id);

    Optional<User> findById(int id);

    void updateRole(int id, UserRole newRole);
}
