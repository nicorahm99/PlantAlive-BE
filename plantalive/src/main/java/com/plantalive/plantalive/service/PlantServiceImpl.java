package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.PlantDAO;
import com.plantalive.plantalive.persistence.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;

    @Autowired
    public PlantServiceImpl(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @Override
    public PlantDTO createPlant(PlantDAO plant) {
        return plantRepository.save(plant).toDTO();
    }

    @Override
    public PlantDTO updatePlant(long plantId, double newTargetHumidity) throws NoSuchElementException {
        PlantDAO currentPlant = plantRepository.findById(plantId).get();
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
}
