package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.PlantDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class PlantDTO {
    private long id;
    private long ownerId;
    private double temperature;
    private double currentHumidity;
    private double targetHumidity;
    private String name;
    private String location;

    public PlantDAO toDAO(){return new PlantDAO(id, ownerId, temperature, currentHumidity, targetHumidity, name, location);}
}
