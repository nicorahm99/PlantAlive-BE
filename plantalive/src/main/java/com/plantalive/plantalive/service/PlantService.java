package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.PlantDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlantService {
    public PlantDTO createPlant(PlantDAO plant);
    public PlantDTO updatePlant(PlantDAO plant);
    public boolean deletePlant(long id);
    public List<PlantDTO> getPlantsFromUser(long userId);
}
