package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.UserDAO;
import com.plantalive.plantalive.persistence.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUserById(long userId){
        UserDAO resultUser = userRepository.findById(userId).orElseThrow();
        return resultUser.toDTO();
    }

    @Override
    public UserDTO createUser(UserDTO newUser) {
        UserDAO createdUser = userRepository.save(newUser.toDAO());
        return createdUser.toSafeDTO();
    }

    @Override
    public UserDTO checkCredentials(String mail, String password) {
        UserDAO foundUser = userRepository.findByMailAndPassword(mail, password).orElseThrow();
        return foundUser.toSafeDTO();
    }
    @Override
    public UserDTO updateUser(UserDTO userToUpdate){
        UserDAO updatedUser = userRepository.save(userToUpdate.toDAO());
        return updatedUser.toSafeDTO();
    }
}
