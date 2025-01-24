package com.stream.tour.domain.naver.naver.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ImageUploadRequest {
    private String name;
    private String originalFilename;
    private String contentType;
    private byte[] content;

    public ImageUploadRequest(MultipartFile file) throws IOException {
        this.name = file.getName();
        this.originalFilename = file.getOriginalFilename();
        this.contentType = file.getContentType();
        this.content = file.getBytes();
    }

    public static List<ImageUploadRequest> createImageUploadRequest(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(multipartFile -> {
                    try {
                        return new ImageUploadRequest(multipartFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
