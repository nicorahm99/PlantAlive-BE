package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    public PlantServiceImpl(PlantRepository plantRepository, UserServiceImpl userService, TopicRepository topicRepository, ImageRepository imageRepository) {
        this.plantRepository = plantRepository;
        this.userService = userService;
        this.topicRepository = topicRepository;
        this.imageRepository = imageRepository;
    }

    private final PlantRepository plantRepository;
    private final UserServiceImpl userService;
    private final TopicRepository topicRepository;
    private final ImageRepository imageRepository;



    @Override
    public PlantDTO createPlant(PlantDAO plant) {
        return plantRepository.save(plant).toDTO();
    }

    @Override
    public PlantDTO updatePlant(PlantDAO plant) throws NoSuchElementException {
        PlantDAO plantToUpdate = plantRepository.findById(plant.getId()).orElseThrow();

        plantToUpdate.setTargetHumidity(plant.getTargetHumidity());
        plantToUpdate.setName(plant.getName());
        plantToUpdate.setLocation(plant.getLocation());

        return plantRepository.save(plantToUpdate).toDTO();
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
        plantRepository.findAllByOwnerId(userId).forEach(plant -> result.add(plant.toDTO()));
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
                plantDTO.getCurrentHumidity(),
                plantDTO.getTargetHumidity(),
                plantDTO.getName(),
                plantDTO.getLocation(),
                topic.getId()
                );
    }

    @Override
    public PlantDTO getPlantById(long plantId) {
        return plantRepository.findById(plantId).orElseThrow().toDTO();
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
}
