package com.stream.tour.global.dto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @See https://velog.io/@qotndus43/%EC%8A%A4%ED%94%84%EB%A7%81-API-%EA%B3%B5%ED%86%B5-%EC%9D%91%EB%8B%B5-%ED%8F%AC%EB%A7%B7-%EA%B0%9C%EB%B0%9C%ED%95%98%EA%B8%B0
 */
@Slf4j
public record ApiResult<T>(
        T data,
        Status status,
        String message
) {

    public static <T> ResponseEntity<ApiResult<T>> createSuccess(T data) {
        return ResponseEntity
                .ok(new ApiResult<>(data, Status.SUCCESS, Status.SUCCESS.getMessage()));
    }

    public static <T> ApiResult<T> createSuccessV2(T data) {
        return new ApiResult<>(data, Status.SUCCESS, Status.SUCCESS.getMessage());
    }

    public static ResponseEntity<ApiResult<Void>> createSuccessWithNoContent() {
        return new ResponseEntity<>(
                new ApiResult<>(null, Status.SUCCESS, Status.SUCCESS.getMessage()),
                HttpStatus.NO_CONTENT
        );
    }

    public static ApiResult<Void> createSuccessWithNoContentV2() {
        return new ApiResult<>(null, Status.SUCCESS, Status.SUCCESS.getMessage());
    }

    public static ResponseEntity<ApiResult<Map<String, String>>> createValidationFail(Map<String, String> errors) {
        return ResponseEntity.badRequest()
                .body(new ApiResult<>(errors, Status.FAIL, Status.FAIL.getMessage()));
    }

    public static ResponseEntity<ApiResult<Map<String, String>>> createValidationFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError fieldError) {
                // get source of error
                boolean isTypeMismatch = Arrays.stream(fieldError.getCodes()).anyMatch(s -> "typeMismatch".equals(s));
                if (isTypeMismatch) {
                    errors.put(fieldError.getField(), "잘못된 데이터 타입 입니다.");
                } else {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            if (!entry.getKey().equals("password")) {
                log.error("ApiResult createFail key = {}, value = {}", entry.getKey(), entry.getValue());
            }
        }

        return ResponseEntity.badRequest()
                .body(new ApiResult<>(errors, Status.FAIL, Status.FAIL.getMessage()));
    }

    public static ResponseEntity<ApiResult<Void>> createFail() {
        return ResponseEntity.badRequest()
                .body(new ApiResult<>(null, Status.FAIL, Status.FAIL.getMessage()));
    }

    public static ResponseEntity<ApiResult<Void>> createFail(String message) {
        return ResponseEntity.badRequest()
                .body(new ApiResult<>(null, Status.FAIL, message));
    }

    public static ResponseEntity<ApiResult<Void>> createFail(Exception e) {
        return ResponseEntity.badRequest()
                .body(new ApiResult<>(null, Status.FAIL, e.getMessage()));
    }


    public static ResponseEntity<ApiResult<Void>> createFail(Exception e, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ApiResult<>(null, Status.FAIL, e.getMessage()), httpStatus);
    }

    public static ResponseEntity<ApiResult<?>> createServerError(String message) {
        return ResponseEntity.internalServerError()
                .body(new ApiResult<>(null, Status.ERROR, message));
    }



    @Getter
    public enum Status {
        SUCCESS("정상적으로 처리되었습니다.")
        , FAIL("잘못된 요청입니다.")
        , ERROR("");

        private final String message;

        Status(String message) {
            this.message = message;
        }
    }
}
