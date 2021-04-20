package com.plantalive.plantalive.controller;

import com.plantalive.plantalive.persistence.ImageDAO;
import com.plantalive.plantalive.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping( "/images")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload/{plantId}")
    public ResponseEntity<HttpStatus> uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable long plantId) throws IOException {
        try {
            imageService.saveImageOfPlant(file, plantId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e){
            logger.error("Image could not be uploaded", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = {"/{plantId}"})
    public ResponseEntity<ImageDAO> getImage(@PathVariable("plantId") long plantId) throws IOException {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageService.getImageOfPlant(plantId));
        } catch (NoSuchElementException e){
            logger.debug("Image could not be returned", e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            logger.error("Unknown Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}

