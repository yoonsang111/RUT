package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;

public class NoRepresentativeImageException extends CustomException {

    public NoRepresentativeImageException() {
        super("대표 이미지가 존재하지 않습니다.");
    }
}
