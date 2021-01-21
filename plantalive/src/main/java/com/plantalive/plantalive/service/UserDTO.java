package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    long id;
    String name;
    String mail;
    String password;

    public UserDAO toDAO() {
        return new UserDAO(id, name, mail, password);
    }
}

