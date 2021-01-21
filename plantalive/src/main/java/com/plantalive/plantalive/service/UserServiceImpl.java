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
    public UserDAO getUserById(long userId){
        return userRepository.findById(userId).orElseThrow();
    }
}
