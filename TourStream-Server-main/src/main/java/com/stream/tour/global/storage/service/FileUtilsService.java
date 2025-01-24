package com.stream.tour.global.storage.service;

import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.children.StorageFileNotFoundException;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.service.UuidHolder;
import com.stream.tour.global.storage.StorageProperties;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.h2.store.fs.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class FileUtilsService {

    private final StorageProperties storageProperties;
    private final String bucketName;
    private final String host;

    @Builder
    public FileUtilsService(
            StorageProperties storageProperties,
            @Value("${cloud.aws.s3.bucket}") String bucketName,
            @Value("${api.tour-stream.host}") String host
    ) {
        this.storageProperties = storageProperties;
        this.bucketName = bucketName;
        this.host = host;
    }

    /**
     * 루트 경로 + 년 + 월 + 일로 파일 경로를 생성한다.
     */
    public Path createDirectories(ClockHolder clockHolder, Path path) {
        LocalDate now = clockHolder.nowInLocalDate();
        String year = String.valueOf(now.getYear());
        String month = String.valueOf(now.getMonthValue());
        String day = String.valueOf(now.getDayOfMonth());

        Path directory = path.resolve(Paths.get(year, month, day));
        try {
            // 경로가 존재하지 않는 경우 절대경로로 표시되어 반복적인 테스트가 안되는 문제 발생
            if (!FileUtils.isDirectory(directory.toString())) {
                if (!directory.toFile().mkdirs()) {
                    log.error("{}, Path = {}, year = {}, month = {}, day = {}", ErrorMessage.FILE_PATH_FORMAT_ERROR, path, year, month, day);
                }
            }

            return Files.createDirectories(directory);
        } catch (IOException e) {
            log.error("{}, Path = {}, year = {}, month = {}, day = {}", ErrorMessage.FILE_PATH_FORMAT_ERROR, path, year, month, day);
            throw new IllegalArgumentException(ErrorMessage.FILE_PATH_FORMAT_ERROR.getMessage());
        }
    }


    public String generateStoredFileName(UuidHolder uuidHolder, String extension) {
        return uuidHolder.random() + "." + extension;
    }

    public void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("{}, Path = {}", ErrorMessage.CANNOT_DELETE_FILE, path);
            throw new IllegalArgumentException(ErrorMessage.CANNOT_DELETE_FILE.getMessage());
        }
    }

    public List<String> extractStoredFilenameFrom(List<String> fileUrls) {
        return fileUrls.stream()
                .map(this::getStoredFileNameFrom)
                .toList();
    }

    public String getStoredFileNameFrom(String fileUrl) {
        int index = fileUrl.lastIndexOf("/");
        if (index == -1) {
            log.error("{}, FileUrl = {}", ErrorMessage.FILE_URL_FORMAT_ERROR, fileUrl);
            throw new IllegalArgumentException(ErrorMessage.FILE_URL_FORMAT_ERROR.getMessage());
        }

        String encodedStoredFilename = fileUrl.substring(index + 1);

        return new String(Base64.getDecoder().decode(encodedStoredFilename));
    }

    public URI loadAsMvcURI(String filePath, String storedFilename) {
        Base64.Encoder encoder = Base64.getEncoder();

        String encodedFilePath = encoder.encodeToString(filePath.getBytes());
        String encodedStoredFilename = encoder.encodeToString(storedFilename.getBytes());

        try {
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .uri(new URI(host))
                    .path("/files/{filePath}/{storedFilename}")
                    .buildAndExpand(encodedFilePath, encodedStoredFilename);
            return uriComponents.toUri();
        } catch (URISyntaxException e) {
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
}
