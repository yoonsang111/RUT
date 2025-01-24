package com.stream.tour.global.storage.service.impl;

import com.stream.tour.global.enums.Directory;
import com.stream.tour.global.enums.FileType;
import com.stream.tour.global.exception.custom.children.StorageFileNotFoundException;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.service.UuidHolder;
import com.stream.tour.global.storage.StoredImageInfo;
import com.stream.tour.global.storage.service.FileUtilsService;
import com.stream.tour.global.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;

import static org.apache.commons.io.FilenameUtils.getExtension;

@Profile({"dev", "test"})
@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {

    private final FileUtilsService fileService;

    @Override
    public List<StoredImageInfo> storeImages(List<MultipartFile> files, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) {
        return files.stream()
                .map(file -> {
                    try {
                        return storeImage(file, fileType, directory, uuidHolder, clockHolder);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }


    @Override
    public StoredImageInfo storeImage(MultipartFile file, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) throws FileNotFoundException {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }

        // 확장자가 일치하지 않는 경우
        String extension = getExtension(file.getOriginalFilename());
        validateExtension(fileType, extension);

        Path filePath = fileService.createDirectories(clockHolder, findDirectory(directory));
        String storedFileName = fileService.generateStoredFileName(uuidHolder, extension);

        try {
            Path destinationFile = filePath
                    .resolve(Paths.get(storedFileName))
                    .normalize()
                    .toAbsolutePath();

            // This is a security check
            if (!destinationFile.getParent().equals(filePath.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory."); // TODO: custom exception으로 변경
            }

            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream())) {
                Files.copy(bufferedInputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return StoredImageInfo.builder()
                    .fullUrl(fileService.loadAsMvcURI(filePath.toString(), storedFileName).toString())
                    .filePath(filePath.toString())
                    .originalFileName(file.getOriginalFilename())
                    .storedName(storedFileName)
                    .extension(extension)
                    .size(file.getSize())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to store file.", e); // TODO: custom exception으로 변경
        }
    }

    @Override
    public StoredImageInfo storeImage(File file, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) throws IOException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }

        // 확장자가 일치하지 않는 경우
        String extension = getExtension(file.getName());
        validateExtension(fileType, extension);

        Path filePath = fileService.createDirectories(clockHolder, findDirectory(directory));
        String storedFileName = fileService.generateStoredFileName(uuidHolder, extension);

        try {
            Path destinationFile = filePath
                    .resolve(Paths.get(storedFileName))
                    .normalize()
                    .toAbsolutePath();

            // This is a security check
            if (!destinationFile.getParent().equals(filePath.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }

            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
                Files.copy(bufferedInputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return StoredImageInfo.builder()
                    .fullUrl(fileService.loadAsMvcURI(filePath.toString(), storedFileName).toString())
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
        Path path = Paths.get(filePath, storedName).normalize();
        fileService.deleteFile(path);
    }

    @Override
    public Path storeExcelFile(MultipartFile file, String storedFileName, ClockHolder clockHolder) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filePath, String storedFilename) {
        Base64.Decoder decoder = Base64.getDecoder();

        filePath = new String(decoder.decode(filePath));
        storedFilename = new String(decoder.decode(storedFilename));

        try {
            Resource resource = new FileSystemResource(loadProduct(filePath, storedFilename));
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(storedFilename);
            }
        } catch (Exception e) {
            throw new StorageFileNotFoundException(storedFilename);
        }
    }

    public String loadProduct(String filePath, String storedFilename) {
        return filePath + "/" + storedFilename;
    }
}
