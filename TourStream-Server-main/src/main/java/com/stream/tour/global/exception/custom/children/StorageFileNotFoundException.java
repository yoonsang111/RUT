package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;

public class StorageFileNotFoundException extends CustomException {
    public StorageFileNotFoundException(String storedFilename) {
        super("Could not read file: " + storedFilename);
    }
}
