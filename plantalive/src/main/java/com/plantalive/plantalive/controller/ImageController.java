package com.plantalive.plantalive.controller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.plantalive.plantalive.persistence.ImageDAO;
import com.plantalive.plantalive.persistence.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping( "/images")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    final
    ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/upload/{plantId}")
    public ResponseEntity<HttpStatus> uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable long plantId) throws IOException {
        logger.debug("Original Image Byte Size - " + file.getBytes().length);
        try {
            ImageDAO img = new ImageDAO(plantId, file.getOriginalFilename(), file.getContentType(),
                    compressBytes(file.getBytes()));
            imageRepository.save(img);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e){
            logger.error("Image could not be uploaded", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping(path = {"/{plantId}"})
    public ResponseEntity<ImageDAO> getImage(@PathVariable("plantId") long plantId) throws IOException {
        final Optional<ImageDAO> retrievedImage = imageRepository.findByPlantId(plantId);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ImageDAO(
                            plantId,
                            retrievedImage.orElseThrow().getName(),
                            retrievedImage.get().getType(),
                            decompressBytes(retrievedImage.get().getPicByte())
                    )
            );
        } catch (NoSuchElementException e){
            logger.debug("Image could not be returned", e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            logger.error("Unknown Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            logException(e);
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ioe) {
            logException(ioe);
        }
        return outputStream.toByteArray();
    }

    private static void logException(Exception e) {
        logger.error("Error wile handling image", e);
    }
}

