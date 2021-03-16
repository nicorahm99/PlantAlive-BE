package com.plantalive.plantalive.persistence;
import lombok.Data;

import javax.persistence.*;
@Entity
@Data
public class ImageDAO {
    public ImageDAO() {
        super();
    }
    public ImageDAO(Long plantId, String name, String type, byte[] picByte) {
        this.plantId = plantId;
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long plantId;
    private String name;
    private String type;
        //image bytes can have large lengths so we specify a value
        //which is more than the default length for picByte column
    @Column(length = 1000000)
    private byte[] picByte;

    public ImageDAO(long plantId) {
        this.plantId = plantId;
    }
}

