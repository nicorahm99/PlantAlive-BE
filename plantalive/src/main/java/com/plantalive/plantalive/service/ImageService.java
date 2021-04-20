package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.ImageDAO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageOfPlant(MultipartFile file, long plantId) throws Exception;

    ImageDAO getImageOfPlant(long plantId);
}
