package org.rockend.service;

import org.rockend.entity.User;
import org.rockend.entity.UserRole;
import org.rockend.entity.dto.UserRegisterDto;

public interface UserService {
//    void save(UserRegisterDto userRegisterDto, UserRole role);
    void save(User user);

    public User getCurrentUser();
}
