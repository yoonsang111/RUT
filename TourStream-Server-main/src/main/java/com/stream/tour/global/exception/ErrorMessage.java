package com.stream.tour.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    ACCESS_DENIED("접근 권한이 없습니다.")
    , NOT_FOUND("등록 되지 않은 데이터 입니다.")
    , SECURITY_ERROR("아이디 또는 비밀번호가 일치하지 않습니다.")
    , AUTHORIZATION_HEADER_NOT_FOUND("인증 헤더가 존재하지 않습니다.")
    , ACCESS_TOKEN_NOT_FOUND("액세스 토큰이 존재하지 않습니다.")
    , INVALID_ACCESS_TOKEN("유효하지 않은 액세스 토큰입니다.")
    , INVALID_REFRESH_TOKEN("유효하지 않은 리프레시 토큰입니다.")
    , NO_REPRESENTATIVE("대표자가 존재하지 않습니다.")

    // FILE
    , FILE_IS_EMPTY("파일을 추가해주세요.")
    , MAX_FILE_COUNT_EXCEEDED("파일은 최대 %s개 까지 등록할 수 있습니다.")
    , CANNOT_DELETE_FILE("파일을 삭제할 수 없습니다.")
    , FILE_PATH_FORMAT_ERROR("파일 경로 형식이 잘못되었습니다.")
    , FILE_URL_FORMAT_ERROR("파일 URL 형식이 잘못되었습니다.")
    , EXTENSION_NOT_ALLOWED("허용되지 않은 파일 확장자 입니다. 허용되는 확장자: %s")
    , STORAGE_FILE_NOT_FOUND("파일을 찾을 수 없습니다.")
    , HAS_MULTI_REPRESENTATIVE_IMAGES("대표 이미지를 여러 개 등록할 수 없습니다.")
    , HAS_NO_REPRESENTATIVE_IMAGE("대표 이미지를 설정해주세요.")
    , CANNOT_REGISTER_FILE("투어스트림 API를 통해서 등록된 이미지만 업로드할 수 있습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
