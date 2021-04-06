package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.PlantDAO;
import com.plantalive.plantalive.persistence.TopicDAO;
import javassist.NotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public interface PlantService {
    public PlantDTO createPlant(PlantDAO plant);
    public PlantDTO updatePlant(PlantDAO updatedPlant) throws NoSuchElementException;
    public boolean deletePlant(long id);
    public List<PlantDTO> getPlantsFromUser(long userId);

    PlantDAO convertPlantDTOtoDAO(PlantDTO plantDTO);

    PlantDTO getPlantById(long plantId);

    List<String> getAllAvailablePlants();

    PlantDAO resolvePlantByTopicName(String topicName) throws NotFoundException;

    Optional<TopicDAO> findTopicByName(String topicName);

    JSONObject encodeTargetHumidityToJSON(double targetHumidity) throws JSONException;
}
