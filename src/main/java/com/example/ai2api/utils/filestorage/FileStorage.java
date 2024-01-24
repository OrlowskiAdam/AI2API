package com.example.ai2api.utils.filestorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class FileStorage {

    @Value("${files-storage.path}")
    private String resourcePath;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public String save(byte[] content, String directoryPath, String fileName) throws IOException {
        log.info("Saving file {}", fileName);

        Path path = Paths.get(createOrGetExistingPathDirectory(directoryPath)
                + "/" + LocalDateTime.now().format(DATE_FORMAT) + "-" + fileName);

        Files.write(path, content);

        return path.toString();
    }

    private Path createOrGetExistingPathDirectory(String directoryPath) throws IOException {
        log.info("Set directory {}", directoryPath);

        Path path = Paths.get(resourcePath +
                "/" + directoryPath);

        return Files.createDirectories(path);
    }

    public byte[] getFileBytes(String filePath) throws IOException {
        log.info("Reading file bytes {}", filePath);
        return Files.readAllBytes(Path.of(filePath));
    }

    public String getFileType(String filePath) throws IOException {
        log.info("Getting file type {}", filePath);
        return Files.probeContentType(Path.of(filePath));
    }
}