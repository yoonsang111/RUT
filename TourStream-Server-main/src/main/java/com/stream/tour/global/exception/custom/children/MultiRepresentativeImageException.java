package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;

public class MultiRepresentativeImageException extends CustomException {

    public MultiRepresentativeImageException() {
        super("대표 이미지를 여러 개 등록할 수 없습니다.");
    }
}
