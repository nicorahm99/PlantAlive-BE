package com.plantalive.plantalive.persistence;

import com.plantalive.plantalive.service.PlantDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private UserDAO owner;

    private double temperature;

    private double currentHumidity;
    private double targetHumidity;

    private String name;
    private String location;


    public PlantDTO toDTO() {
        return new PlantDTO(id, owner.getId(), temperature, currentHumidity, targetHumidity, name, location);
    }
}
