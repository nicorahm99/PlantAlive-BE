package com.plantalive.plantalive.controller;

import com.plantalive.plantalive.service.PlantDTO;
import com.plantalive.plantalive.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    PlantService plantService;

    @GetMapping("/fromUser/{userId}")
    public ResponseEntity<List<PlantDTO>> getPlantsFromUserById(@PathVariable long userId){
        return new ResponseEntity<>(plantService.getPlantsFromUser(userId), HttpStatus.NOT_FOUND);
    }
}
