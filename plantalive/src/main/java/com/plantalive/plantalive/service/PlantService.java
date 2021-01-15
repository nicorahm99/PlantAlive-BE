package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.PlantDAO;
import org.springframework.stereotype.Service;

@Service
public interface PlantService {
    public PlantDTO createPlant(PlantDAO plant);
    public PlantDTO updatePlant(PlantDAO plant);
    public boolean deletePlant(long id);
}
