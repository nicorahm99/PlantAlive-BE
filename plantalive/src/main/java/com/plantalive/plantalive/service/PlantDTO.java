package com.plantalive.plantalive.service;

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
    private int waterLevel;
    private double currentHumidity;
    private double targetHumidity;
    private String name;
    private String location;
    private String topicName;

    public PlantDTO(long id, long ownerId, double temperature, int waterLevel, double currentHumidity, double targetHumidity, String name, String location) {
        this.id = id;
        this.ownerId =ownerId;
        this.temperature = temperature;
        this.waterLevel = waterLevel;
        this.currentHumidity = currentHumidity;
        this.targetHumidity = targetHumidity;
        this.name = name;
        this.location = location;
    }
}
