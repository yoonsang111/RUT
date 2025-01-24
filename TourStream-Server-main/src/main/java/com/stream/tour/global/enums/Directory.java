package com.stream.tour.global.enums;

import lombok.Getter;

@Getter
public enum Directory {
    PRODUCT("product"),
    PARTNER("partner");

    private final String directory;

    Directory(String directory) {
        this.directory = directory;
    }
}
