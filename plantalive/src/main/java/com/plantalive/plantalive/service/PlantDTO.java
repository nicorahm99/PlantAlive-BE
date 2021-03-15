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
    private double currentHumidity;
    private double targetHumidity;
    private String name;
    private String location;
    private String topicName;
    private long topicId;

    public PlantDTO(long id, long ownerId, double temperature, double currentHumidity, double targetHumidity, String name, String location, long topicId) {
        this.id = id;
        this.ownerId =ownerId;
        this.temperature = temperature;
        this.currentHumidity = currentHumidity;
        this.targetHumidity = targetHumidity;
        this.name = name;
        this.location = location;
        this.topicId = topicId;
    }
}
