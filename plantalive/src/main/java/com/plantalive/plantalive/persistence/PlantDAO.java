package com.plantalive.plantalive.persistence;

import com.plantalive.plantalive.service.PlantDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long ownerId;

    private double temperature;

    private double currentHumidity;
    private double targetHumidity;

    private String name;
    private String location;


    public PlantDTO toDTO() {
        return new PlantDTO(id, ownerId, temperature, currentHumidity, targetHumidity, name, location);
    }
}
