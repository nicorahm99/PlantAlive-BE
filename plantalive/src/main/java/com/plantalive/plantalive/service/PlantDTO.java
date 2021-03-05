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
    private long topicId;
}
