package com.plantalive.plantalive.service;

import com.plantalive.plantalive.persistence.ImageDAO;
import com.plantalive.plantalive.persistence.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageServiceImpl implements ImageService {

    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void saveImageOfPlant(MultipartFile file, long plantId) throws Exception {
        logger.debug("Original Image Byte Size - " + file.getBytes().length);
        byte [] compressedBytes = compressBytes(file.getBytes());
        if (compressedBytes.length > 1000000) throw new IllegalArgumentException();
        ImageDAO img = imageRepository.findByPlantId(plantId)
                .orElse(new ImageDAO(plantId));
        img.setPicByte(compressedBytes);
        img.setType(file.getContentType());
        img.setName(file.getOriginalFilename());
        imageRepository.save(img);
    }

    @Override
    public ImageDAO getImageOfPlant(long plantId) {
        Optional<ImageDAO> retrievedImage = imageRepository.findByPlantId(plantId);
        return new ImageDAO(
                plantId,
                retrievedImage.orElseThrow().getName(),
                retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte())
        );
    }

    private byte[] compressBytes(byte[] data) {
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
        logger.debug("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }


    private byte[] decompressBytes(byte[] data) {
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
    private void logException(Exception e) {
        logger.error("Error wile handling image", e);
    }

}
