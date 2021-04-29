package com.plantalive.plantalive.service;

public interface UserService {
    UserDTO getUserById(long userId);
    UserDTO createUser(UserDTO newUser);
    UserDTO checkCredentials(String mail, String password);

    UserDTO updateUser(UserDTO userToUpdate);
}
