package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.PlantDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface PlantService {
    public PlantDTO createPlant(PlantDAO plant);
    public PlantDTO updatePlant(PlantDAO updatedPlant) throws NoSuchElementException;
    public boolean deletePlant(long id);
    public List<PlantDTO> getPlantsFromUser(long userId);

    PlantDAO convertPlantDTOtoDAO(PlantDTO plantDTO);

    PlantDTO getPlantById(long plantId);

    List<String> getAllAvailablePlants();
}
