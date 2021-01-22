package com.plantalive.plantalive.persistence;

import com.plantalive.plantalive.service.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;

    @Column(unique = true)
    String mail;

    String password;

    public UserDTO toSafeDTO() {
        return new UserDTO(id, name, mail, null);
    }

    public UserDTO toDTO() {
        return new UserDTO(id, name, mail, password);
    }


}
