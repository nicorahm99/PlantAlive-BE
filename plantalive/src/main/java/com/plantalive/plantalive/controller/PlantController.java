package com.plantalive.plantalive.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    @GetMapping("/fromUser/{userId}")
    public ResponseEntity getPlantsFromUserById(@PathVariable long userId){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
