package com.plantalive.plantalive.persistence;

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

    private int waterLevel;

    private double currentHumidity;
    private double targetHumidity;

    private String name;
    private String location;

    private long topicId;
}
