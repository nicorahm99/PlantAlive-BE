package com.plantalive.plantalive.persistence;

import com.plantalive.plantalive.service.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;
    String mail;
    String password;

    public UserDTO toSafeDTO() {
        return new UserDTO(id, name, mail, null);
    }

    public UserDTO toDTO() {
        return new UserDTO(id, name, mail, password);
    }


}
