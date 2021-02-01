package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.UserDAO;

public interface UserService {
    UserDTO getUserById(long userId);
    UserDTO createUser(UserDTO newUser);
    UserDTO checkCredentials(String mail, String password);
}
