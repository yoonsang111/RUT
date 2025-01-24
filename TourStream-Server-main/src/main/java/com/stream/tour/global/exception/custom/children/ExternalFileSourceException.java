package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;

public class ExternalFileSourceException extends CustomException {

    public ExternalFileSourceException() {
        super("투어스트림 API를 통해서 등록된 이미지만 업로드할 수 있습니다.");
    }
}
