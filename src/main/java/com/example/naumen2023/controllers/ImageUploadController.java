package com.example.naumen2023.controllers;

import com.example.naumen2023.services.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageUploadController {
    private final ImageService imageService;

    @Autowired
    public ImageUploadController(ImageService imageService) {
        this.imageService = imageService;
    }
        @PostMapping("/upload-image")
        public ResponseEntity<Map<String, String>> handleImageUpload(@RequestParam("file") MultipartFile file) {
            try {
                String imageUrl = imageService.saveImage(file);
                Map<String, String> response = new HashMap<>();
                response.put("url", imageUrl);

                return ResponseEntity.ok().body(response);
            } catch (IOException e) {
                e.printStackTrace();
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Ошибка сервера");

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        }
}
