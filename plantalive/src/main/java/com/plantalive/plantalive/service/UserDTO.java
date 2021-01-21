package com.plantalive.plantalive.service;

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
}

