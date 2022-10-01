package com.dmdev.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.*;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket:C:\\Users\\marsel.asatov\\javaProjects\\spring-project-dmdev\\images}")
    private final String bucket;

    @SneakyThrows
    public void upload(String image, InputStream content){
        Path fullPath = Path.of(bucket, image);
        try (content){
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String image){
        Path fullPath = Path.of(bucket, image);

        return Files.isExecutable(fullPath)
                ? Optional.of(Files.readAllBytes(fullPath))
                : Optional.empty();
    }
}
