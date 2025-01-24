package com.stream.tour.common.mock;

import com.stream.tour.global.enums.Directory;
import com.stream.tour.global.enums.FileType;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.service.UuidHolder;
import com.stream.tour.global.storage.StoredImageInfo;
import com.stream.tour.global.storage.service.FileUtilsService;
import com.stream.tour.global.storage.service.StorageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class FakeStorageService implements StorageService {

    private final FileUtilsService fileUtilsService;

    public FakeStorageService(FileUtilsService fileUtilsService) {
        this.fileUtilsService = fileUtilsService;
    }

    @Override
    public List<StoredImageInfo> storeImages(List<MultipartFile> files, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) {
        return List.of();
    }

    @Override
    public StoredImageInfo storeImage(MultipartFile file, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) throws FileNotFoundException {
        return null;
    }

    @Override
    public StoredImageInfo storeImage(File file, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) throws IOException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }

        // 확장자가 일치하지 않는 경우
        String extension = getExtension(file.getName());
        validateExtension(fileType, extension);

        Path filePath = fileUtilsService.createDirectories(clockHolder, findDirectory(directory));
        String storedFileName = fileUtilsService.generateStoredFileName(uuidHolder, extension);

        try {
            Path destinationFile = filePath
                    .resolve(Paths.get(storedFileName))
                    .normalize();

            // This is a security check
            if (!destinationFile.getParent().equals(filePath)) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }

            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
                Files.copy(bufferedInputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return StoredImageInfo.builder()
                    .fullUrl(fileUtilsService.loadAsMvcURI(filePath.toString(), storedFileName).toString())
                    .filePath(filePath.toString())
                    .originalFileName(file.getName())
                    .storedName(storedFileName)
                    .extension(extension)
                    .size(Files.size(file.toPath()))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public void deleteImage(String filePath, String storedName) {

    }

    @Override
    public Path storeExcelFile(MultipartFile file, String storedFileName, ClockHolder clockHolder) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filePath, String storedFilename) {
        return new ClassPathResource("static/images/product/phone.jpeg");
    }
}
