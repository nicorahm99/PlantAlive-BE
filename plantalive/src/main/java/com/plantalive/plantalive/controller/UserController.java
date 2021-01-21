package com.plantalive.plantalive.controller;

import com.plantalive.plantalive.service.UserDTO;
import com.plantalive.plantalive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO newUser){
        try {
            return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth")
    public ResponseEntity<HttpStatus> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest){
        if (userService.checkCredentials(authenticationRequest.getId(), authenticationRequest.getPassword())){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
