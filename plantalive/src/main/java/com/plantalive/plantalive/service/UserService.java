package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.UserDAO;

public interface UserService {
    UserDAO getUserById(long userId);
}
