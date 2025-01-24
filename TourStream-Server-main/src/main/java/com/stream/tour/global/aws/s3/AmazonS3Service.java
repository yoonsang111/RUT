package com.stream.tour.global.aws.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.stream.tour.global.enums.Directory;
import com.stream.tour.global.enums.FileType;
import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.children.StorageFileNotFoundException;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.service.UuidHolder;
import com.stream.tour.global.storage.StorageProperties;
import com.stream.tour.global.storage.StoredImageInfo;
import com.stream.tour.global.storage.service.FileUtilsService;
import com.stream.tour.global.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import static org.apache.commons.io.FilenameUtils.getExtension;

@Profile("prod")
@Slf4j
@RequiredArgsConstructor
@Service
public class AmazonS3Service implements StorageService {

    private final AmazonS3Client amazonS3Client;
    private final StorageProperties storageProperties;
    private final FileUtilsService fileService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public List<StoredImageInfo> storeImages(List<MultipartFile> files, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) {
        validateFiles(files);

        if (files.size() > 20) {
            throw new IllegalArgumentException(
                    String.format(ErrorMessage.MAX_FILE_COUNT_EXCEEDED.getMessage(), 20)
            );
        }

        return files.stream()
                .map(file -> storeImage(file, fileType, directory, uuidHolder, clockHolder))
                .toList();
    }

    @Override
    public StoredImageInfo storeImage(MultipartFile file, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) {
        validateFile(file);

        // 확장자가 일치하지 않는 경우
        String extension = getExtension(file.getOriginalFilename());
        validateExtension(fileType, extension);

        Path filePath = fileService.createDirectories(clockHolder, findDirectory(directory));
        String storedFileName = fileService.generateStoredFileName(uuidHolder, file.getOriginalFilename());

        String fullUrl = uploadToS3(file, filePath, storedFileName);

        return StoredImageInfo.builder()
                .fullUrl(fullUrl)
                .filePath(filePath.toString())
                .originalFileName(file.getOriginalFilename())
                .storedName(storedFileName)
                .extension(extension)
                .size(file.getSize())
                .build();
    }

    @Override
    public StoredImageInfo storeImage(File file, FileType fileType, Directory directory, UuidHolder uuidHolder, ClockHolder clockHolder) throws IOException {
        validateFile(file);

        // 확장자가 일치하지 않는 경우
        String extension = getExtension(file.getName());
        validateExtension(fileType, extension);

        Path filePath = fileService.createDirectories(clockHolder, findDirectory(directory));
        String storedFileName = fileService.generateStoredFileName(uuidHolder, file.getName());

        String fullUrl = uploadToS3(file, filePath, storedFileName);

        return StoredImageInfo.builder()
                .fullUrl(fullUrl)
                .filePath(filePath.toString())
                .originalFileName(file.getName())
                .storedName(storedFileName)
                .extension(extension)
                .size(file.length())
                .build();
    }

    @Override
    public void deleteImage(String filePath, String storedName) {

    }

    @Override
    public Path storeExcelFile(MultipartFile file, String storedFileName, ClockHolder clockHolder) {

        validateFile(file);

        Path filePath = fileService.createDirectories(clockHolder, Paths.get(bucketName + "/excel"));
        uploadToS3(file, filePath, storedFileName);
        return filePath;
    }

    // === 내부 메소드 === //
    private String uploadToS3(MultipartFile file, Path filePath, String storedFileName) {
        String destinationFile = Paths.get(bucketName)
                .resolve(filePath)
                .resolve(Paths.get(storedFileName))
                .normalize()
                .toString();

        ObjectMetadata objectMetadata = createObjectMetadata(file);

        uploadFileToS3(file, destinationFile, objectMetadata);

        return fileService.loadAsMvcURI(filePath.toString(), storedFileName).toString();
    }

    private String uploadToS3(File file, Path filePath, String storedFileName) throws IOException {
        String destinationFile = Paths.get(bucketName)
                .resolve(filePath)
                .resolve(Paths.get(storedFileName))
                .normalize()
                .toString();

        ObjectMetadata objectMetadata = createObjectMetadata(file);

        uploadFileToS3(file, destinationFile, objectMetadata);

        return fileService.loadAsMvcURI(filePath.toString(), storedFileName).toString();
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    private ObjectMetadata createObjectMetadata(File file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(Files.probeContentType(file.toPath()));
        objectMetadata.setContentLength(file.length());
        return objectMetadata;
    }

    private void uploadFileToS3(MultipartFile file, String destinationFile, ObjectMetadata objectMetadata) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream())) {
            amazonS3Client.putObject(bucketName, destinationFile, bufferedInputStream, objectMetadata);
            log.info("File upload is completed. (bucketName: {}, destinationFile: {})", bucketName, destinationFile);
        } catch (Exception e) {
            log.error("Failed to upload file to S3. (bucketName: {}, destinationFile: {})", bucketName, destinationFile, e);
            throw new RuntimeException(e);
        }
    }

    private void uploadFileToS3(File file, String destinationFile, ObjectMetadata objectMetadata) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            amazonS3Client.putObject(bucketName, destinationFile, bufferedInputStream, objectMetadata);
            log.info("File upload is completed. (bucketName: {}, destinationFile: {})", bucketName, destinationFile);
        } catch (Exception e) {
            log.error("Failed to upload file to S3. (bucketName: {}, destinationFile: {})", bucketName, destinationFile, e);
            throw new RuntimeException(e);
        }
    }

    public Resource loadAsResource(String filePath, String storedFilename) {
        Base64.Decoder decoder = Base64.getDecoder();

        filePath = new String(decoder.decode(filePath));
        storedFilename = new String(decoder.decode(storedFilename));

        try {
            Resource resource = new UrlResource(loadProduct(filePath, storedFilename));
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(storedFilename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException(storedFilename);
        }
    }

    public String loadProduct(String filePath, String storedFilename) {
        StorageProperties.Location location = storageProperties.getLocation();

        return location.getS3() + "/" + bucketName + "/" + filePath + "/" + storedFilename;
    }

    private String getS3Url(String destinationFile) {
        return storageProperties.getLocation().getS3() + "/" + destinationFile;
    }

    private void validateFile(MultipartFile file) {
        if (isEmpty(file)) {
            throw new IllegalArgumentException(ErrorMessage.FILE_IS_EMPTY.getMessage());
        }
    }

    private void validateFile(File file) {
        if (isEmpty(file)) {
            throw new IllegalArgumentException(ErrorMessage.FILE_IS_EMPTY.getMessage());
        }
    }

    private void validateFiles(List<MultipartFile> files) {
        if (isEmpty(files)) {
            throw new IllegalArgumentException(ErrorMessage.FILE_IS_EMPTY.getMessage());
        }
    }

    private boolean isEmpty(List<MultipartFile> files) {
        return files == null || files.isEmpty();
    }

    private boolean isEmpty(MultipartFile file) {
        return file == null || file.isEmpty();
    }

    private boolean isEmpty(File file) {
        return file == null || file.exists();
    }
}
