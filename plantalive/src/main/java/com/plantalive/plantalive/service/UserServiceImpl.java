package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.UserDAO;
import com.plantalive.plantalive.persistence.UserRepository;
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
        return userRepository.save(newUser.toDAO()).toSafeDTO();
    }

    @Override
    public boolean checkCredentials(long id, String password) {
        return userRepository.existsByIdAndPassword(id, password);
    }
}
