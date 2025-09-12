package com.myproject.jobportal.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    // Directory where resumes will be stored
    private final Path storageLocation = Paths.get("uploads/resumes");

    public FileStorageService() {
        try {
            if (!Files.exists(storageLocation)) {
                Files.createDirectories(storageLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage folder", e);
        }
    }

    public String saveFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Cannot store empty file.");
            }

            // Generate unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Full path
            Path destination = storageLocation.resolve(fileName);

            // Copy file
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            // Return path or URL (here: relative path)
            return "/uploads/resumes/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
