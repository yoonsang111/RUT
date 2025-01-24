package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String datasource, Long id) {
        super(datasource + "에서 ID " + id + "를 찾을 수 없습니다.");
    }

    public ResourceNotFoundException(String datasource, String email) {
        super(datasource + "에서 EMAIL " + email + "를 찾을 수 없습니다.");
    }
}
