package com.example.naumen2023.services.image;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class ImageService {

    private final ResourceLoader resourceLoader;

    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String saveImage(MultipartFile file) throws IOException {
        // Получаем путь к директории static/images
        String uploadDir = "src/main/resources/static/images";

        // Создаем путь к директории
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();

        // Убеждаемся, что директория существует
        Files.createDirectories(uploadPath);

        // Генерируем уникальное имя файла
        String fileName = System.currentTimeMillis() + "-" + Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "_");

        // Создаем путь к файлу
        Path filePath = uploadPath.resolve(fileName);

        // Копируем файл в целевую директорию
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Возвращаем URL сохраненного изображения
        return "/images/" + fileName;
    }

    public Resource loadImageAsResource(String fileName) throws IOException {
        // Получаем путь к файлу
        Path filePath = Path.of("static/images").resolve(fileName).normalize();
        Resource resource = resourceLoader.getResource("file:" + filePath.toAbsolutePath().toString());

        if (resource.exists()) {
            return resource;
        } else {
            throw new IOException("File not found: " + fileName);
        }
    }
}