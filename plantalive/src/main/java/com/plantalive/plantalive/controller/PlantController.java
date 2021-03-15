package com.plantalive.plantalive.controller;

import com.plantalive.plantalive.MQTT.MqttChannelInfo;
import com.plantalive.plantalive.MQTT.MqttChannelNew;
import com.plantalive.plantalive.MQTT.MqttChannelTargetHumidity;
import com.plantalive.plantalive.persistence.TopicRepository;
import com.plantalive.plantalive.service.MQTTService;
import com.plantalive.plantalive.service.PlantDTO;
import com.plantalive.plantalive.service.PlantService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/plants")
public class PlantController {

    private final PlantService plantService;
    private final MQTTService mqttService;
    private final MqttChannelNew mqttChannelNew;
    private final TopicRepository topicRepository;

    private final Logger logger = LoggerFactory.getLogger(PlantController.class);

    @Autowired
    public PlantController(PlantService plantService, MQTTService mqttService, MqttChannelNew mqttChannelNew, TopicRepository topicRepository) {
        this.plantService = plantService;
        this.mqttService = mqttService;
        this.mqttChannelNew = mqttChannelNew;
        this.topicRepository = topicRepository;
    }

    @PostMapping
    public ResponseEntity<PlantDTO> createPlant(@RequestBody PlantDTO plant){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    plantService.createPlant(plantService.convertPlantDTOtoDAO(plant))
            );
        } catch (Exception e){
            logger.error("Plant from user " + plant.getOwnerId() + " could not be created", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<PlantDTO> updatePlant(@RequestBody PlantDTO plant){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    plantService.updatePlant(plantService.convertPlantDTOtoDAO(plant))
            );
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

    @GetMapping("/available")
    public ResponseEntity<List<String>> getAllAvailablePlants(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(plantService.getAllAvailablePlants());
        } catch (Exception e){
            logger.error("Available Plants could not be read!", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{plantId}")
    public ResponseEntity<Boolean> deletePlantById(@PathVariable long plantId){
        try{
            return new ResponseEntity<>(plantService.deletePlant(plantId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostConstruct
    private void initExistingMqttChannels(){
        try {
            logger.info("Trying to subscribe for Topic 'new'");
            mqttService.subscribeTopic(mqttChannelNew);
            topicRepository.findAll().forEach(topicDAO -> {
                try {
                    logger.info("Trying to subscribe for Topic {}", topicDAO.getTopicName());
                    mqttService.subscribeTopic(new MqttChannelInfo(topicDAO.getTopicName()));
                    mqttService.subscribeTopic(new MqttChannelTargetHumidity(topicDAO.getTopicName()));
                    logger.info("Successfully subscribed for Topics {}/info and /targetHumidity", topicDAO.getTopicName());
                } catch (MqttException e) {
                    logger.error("Channel '{}' could not be subscribed", topicDAO.getTopicName());
                    e.printStackTrace();
                }
            });
        } catch (MqttException e) {
            logger.error("Channel 'new' could not be subscribed");
            e.printStackTrace();
        }
    }
}
