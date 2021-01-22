package com.plantalive.plantalive.controller;

import com.plantalive.plantalive.service.UserDTO;
import com.plantalive.plantalive.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);



    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO newUser){
        try {
            return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            logger.error("Exception", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth")
    public ResponseEntity<HttpStatus> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest){
        if (userService.checkCredentials(authenticationRequest.getMail(), authenticationRequest.getPassword())){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
