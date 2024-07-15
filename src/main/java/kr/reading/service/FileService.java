package kr.reading.service;

import kr.reading.global.exception.FileSavedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.upload-dir}") private String uploadDir;

    public String saveImage(MultipartFile image) {
        try {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, image.getBytes());

            return uploadDir + fileName;
        } catch (IOException e) {
            throw new FileSavedException();
        }
    }

}
