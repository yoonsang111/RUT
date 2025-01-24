package com.stream.tour.global.storage.service;

import com.stream.tour.global.enums.Directory;
import com.stream.tour.global.enums.FileType;
import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.service.UuidHolder;
import com.stream.tour.global.storage.StoredImageInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface StorageService {

    List<StoredImageInfo> storeImages(List<MultipartFile> files, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder);

    StoredImageInfo storeImage(MultipartFile file, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) throws FileNotFoundException;

    StoredImageInfo storeImage(File file, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) throws IOException;

    void deleteImage(String filePath, String storedName);

    Path storeExcelFile(MultipartFile file, String storedFileName, ClockHolder clockHolder);

    Resource loadAsResource(String filePath, String storedFilename);

    default Path findDirectory(Directory directory) {
        return switch (directory) {
            case PRODUCT -> Paths.get(Directory.PRODUCT.getDirectory());
            case PARTNER -> Paths.get(Directory.PARTNER.getDirectory());
        };
    }

    default void validateExtension(FileType fileType, String extension) {
        if (!fileType.getExtensions().contains(extension.toLowerCase())) {
            throw new IllegalArgumentException(
                    String.format(ErrorMessage.EXTENSION_NOT_ALLOWED.getMessage(), fileType.getExtensions())
            );
        }
    }
}
