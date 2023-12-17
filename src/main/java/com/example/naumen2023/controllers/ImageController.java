package com.example.naumen2023.controllers;

import com.example.naumen2023.services.image.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) throws IOException {
        Resource resource = imageService.loadImageAsResource(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(resource.getURI())));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + fileName);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}