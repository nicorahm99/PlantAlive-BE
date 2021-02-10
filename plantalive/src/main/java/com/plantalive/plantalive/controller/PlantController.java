package com.plantalive.plantalive.controller;

import com.plantalive.plantalive.service.PlantDTO;
import com.plantalive.plantalive.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/plants")
public class PlantController {

    private final PlantService plantService;

    private final Logger logger = LoggerFactory.getLogger(PlantController.class);

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createPlant(@RequestBody PlantDTO plant){
        try {
            plantService.createPlant(plantService.convertPlantDTOtoDAO(plant));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e){
            logger.error("Plant from user " + plant.getOwnerId() + " could not be created", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{plantId}")
    public ResponseEntity<PlantDTO> getPlantById(@PathVariable long plantId){
        try{
            return new ResponseEntity<>(plantService.getPlantById(plantId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/fromUser/{userId}")
    public ResponseEntity<List<PlantDTO>> getPlantsFromUserById(@PathVariable long userId){
        try{
            logger.debug("Trying to find plants form user" + userId);
            return new ResponseEntity<>(plantService.getPlantsFromUser(userId), HttpStatus.OK);
        } catch (Exception e){
            logger.error("Error while trying to get plants from User " + userId,e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/targetHumidity/{plantId}/{newTargetHumidity}")
    public ResponseEntity<PlantDTO> updateTargetHumidity(@PathVariable long plantId, @PathVariable double newTargetHumidity){
        try{
            return new ResponseEntity<>(plantService.updatePlant(plantId, newTargetHumidity), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            logger.error("Could not update plant with id " + plantId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
