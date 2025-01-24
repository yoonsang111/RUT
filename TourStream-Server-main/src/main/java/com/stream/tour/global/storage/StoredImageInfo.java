package com.stream.tour.global.storage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoredImageInfo {
    private String fullUrl;
    private String filePath;
    private String originalFileName;
    private String storedName;
    private String extension;
    private long size;
}