package com.Detroit.detroit.library;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUpload {

    @Value("${file.upload-dir:uploads}") // Configurable directory
    private String uploadDir;

    @Value("${server.url:http://localhost:8080}") // Configurable server URL
    private String serverUrl;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("No file selected.");
        }

        // Get the current working directory
        String projectDir = System.getProperty("user.dir");

        // Define the directory for uploads
        String uploadDir = projectDir + File.separator + "/src/main/resources/uploads" + File.separator;


        // Ensure the upload directory exists
        File dir = new File(uploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directories.");
        }

        // Generate a unique file name
        String originalFileName = file.getOriginalFilename();
        String safeFileName = Objects.requireNonNull(originalFileName).replaceAll("[^a-zA-Z0-9._-]", "_");
        String uniqueFileName = UUID.randomUUID() + "_" + safeFileName;

        // Save the file locally
        Path filePath = Paths.get(uploadDir + File.separator + uniqueFileName);
        file.transferTo(filePath.toFile());

        // Generate and return public URL
        return serverUrl + "/media/" + uniqueFileName;
    }

}

