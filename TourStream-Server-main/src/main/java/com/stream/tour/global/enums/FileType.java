package com.stream.tour.global.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum FileType {
    IMAGE(List.of("jpg", "jpeg", "png"));

    private List<String> extensions;

    FileType(List<String> extensions) {
        this.extensions = extensions;
    }
}
