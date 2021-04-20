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

import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
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

    @PostMapping("/auth")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            UserDTO user = userService.checkCredentials(authenticationRequest.getMail(), authenticationRequest.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e){
            logger.error("Uncaught Exception in UserController", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userToUpdate){
        try {
            if(userService.getUserById(userToUpdate.getId()) != null){
                return new ResponseEntity<>(userService.updateUser(userToUpdate), HttpStatus.OK);
            }
            throw new DataIntegrityViolationException("User doesnt exist!");
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            logger.error("Exception", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
