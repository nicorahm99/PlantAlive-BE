package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.*;
import javassist.NotFoundException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    public PlantServiceImpl(PlantRepository plantRepository, UserServiceImpl userService, TopicRepository topicRepository, ImageRepository imageRepository, MQTTService mqttService) {
        this.plantRepository = plantRepository;
        this.userService = userService;
        this.topicRepository = topicRepository;
        this.imageRepository = imageRepository;
        this.mqttService = mqttService;
    }

    private final PlantRepository plantRepository;
    private final UserServiceImpl userService;
    private final TopicRepository topicRepository;
    private final ImageRepository imageRepository;
    private final MQTTService mqttService;



    @Override
    public PlantDTO createPlant(PlantDAO plant) {
        var savedPlant = plantRepository.save(plant);
        return convertPlantDAOtoDTO(savedPlant);
    }

    @Transactional
    @Override
    public PlantDTO updatePlant(PlantDAO plant) throws NoSuchElementException, MqttException {
        PlantDAO plantToUpdate = plantRepository.findById(plant.getId()).orElseThrow();

        plantToUpdate.setTargetHumidity(plant.getTargetHumidity());
        plantToUpdate.setName(plant.getName());
        plantToUpdate.setLocation(plant.getLocation());

        TopicDAO topic = topicRepository.findById(plantToUpdate.getTopicId()).orElseThrow();
        mqttService.publishMqttMessage(String.valueOf(plant.getTargetHumidity()), topic.getTopicName());
        var updatedPlant = plantRepository.save(plantToUpdate);
        return convertPlantDAOtoDTO(updatedPlant);
    }


    @Override
    @Transactional
    public boolean deletePlant(long id) {
        try {
            imageRepository.deleteAllByPlantId(id);
            topicRepository.findAllByPlantId(id).forEach(
                    topicDAO -> {
                        topicDAO.setPlantId(0);
                        topicRepository.save(topicDAO);
                    });
            plantRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public List<PlantDTO> getPlantsFromUser(long userId) {
        List<PlantDTO> result = new ArrayList<>();
        plantRepository.findAllByOwnerId(userId).forEach(plant -> result.add(convertPlantDAOtoDTO(plant)));
        return result;
    }

    @Override
    public PlantDAO convertPlantDTOtoDAO(PlantDTO plantDTO){
        UserDAO owner = userService.getUserById(plantDTO.getOwnerId()).toDAO();
        TopicDAO topic = topicRepository.findByTopicName(plantDTO.getTopicName()).orElseThrow();
        return new PlantDAO(
                plantDTO.getId(),
                owner,
                plantDTO.getTemperature(),
                plantDTO.getWaterLevel(),
                plantDTO.getCurrentHumidity(),
                plantDTO.getTargetHumidity(),
                plantDTO.getName(),
                plantDTO.getLocation(),
                topic.getId()
                );
    }

    public PlantDTO convertPlantDAOtoDTO(PlantDAO plantDAO){
        TopicDAO topicDAO = topicRepository.findById(plantDAO.getTopicId()).orElse(new TopicDAO(""));
        return new PlantDTO(
                plantDAO.getId(),
                plantDAO.getOwner().getId(),
                plantDAO.getTemperature(),
                plantDAO.getWaterLevel(),
                plantDAO.getCurrentHumidity(),
                plantDAO.getTargetHumidity(),
                plantDAO.getName(),
                plantDAO.getLocation(),
                topicDAO.getTopicName()
        );
    }

    @Override
    public PlantDTO getPlantById(long plantId) {
        var plant = plantRepository.findById(plantId).orElseThrow();
        return convertPlantDAOtoDTO(plant);
    }

    @Override
    public List<String> getAllAvailablePlants() {
        var availableTopics = topicRepository.findAllByPlantId(0);
        List<String> availableTopicNames = new ArrayList<>();
        for (TopicDAO topic:availableTopics
             ) {
            availableTopicNames.add(topic.getTopicName());
        }
        return availableTopicNames;
    }

    @Override
    public PlantDAO resolvePlantByTopicName(String topicString) throws NotFoundException {
        String topicName = topicString.split("/")[0];
        TopicDAO topic = topicRepository.findByTopicName(topicName).orElseThrow();
        if (topic.getPlantId() == 0){
            throw new NotFoundException(topicName);
        }
        return plantRepository.findById(topic.getPlantId()).orElseThrow();
    }

    @Override
    public Optional<TopicDAO> findTopicByName(String topicName){
        return topicRepository.findByTopicName(topicName);
    }
}
