package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.PlantDAO;
import com.plantalive.plantalive.persistence.PlantRepository;
import com.plantalive.plantalive.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    public PlantServiceImpl(PlantRepository plantRepository, UserServiceImpl userService) {
        this.plantRepository = plantRepository;
        this.userService = userService;
    }

    private final PlantRepository plantRepository;
    private final UserServiceImpl userService;


    @Override
    public PlantDTO createPlant(PlantDAO plant) {
        return plantRepository.save(plant).toDTO();
    }

    @Override
    public PlantDTO updatePlant(long plantId, double newTargetHumidity) throws NoSuchElementException {
        PlantDAO currentPlant = plantRepository.findById(plantId).orElseThrow();
        currentPlant.setTargetHumidity(newTargetHumidity);
        return plantRepository.save(currentPlant).toDTO();
    }


    @Override
    public boolean deletePlant(long id) {
        try {
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
        return new PlantDAO(
                plantDTO.getId(),
                owner,
                plantDTO.getTemperature(),
                plantDTO.getCurrentHumidity(),
                plantDTO.getTargetHumidity(),
                plantDTO.getName(),
                plantDTO.getLocation());
    }

    @Override
    public PlantDTO getPlantById(long plantId) {
        return plantRepository.findById(plantId).orElseThrow().toDTO();
    }
}
