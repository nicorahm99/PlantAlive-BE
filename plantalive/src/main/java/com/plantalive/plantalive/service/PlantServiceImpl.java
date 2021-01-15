package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.PlantDAO;
import com.plantalive.plantalive.persistence.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    PlantRepository plantRepository;

    @Override
    public PlantDTO createPlant(PlantDAO plant) {
        return plantRepository.save(plant).toDTO();
    }

    @Override
    public PlantDTO updatePlant(PlantDAO plant) {
        return null;
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
}
