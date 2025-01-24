package com.stream.tour.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.stream.tour.global.dto.ApiResult;
import com.stream.tour.global.exception.custom.CustomException;
import com.stream.tour.global.exception.custom.children.AccessDeniedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 해당 클래스에 대한 모든 로그는 aop에서 처리
 * @see com.stream.tour.global.aop.aspect.ExceptionLogAspect
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiResult<Void>> handlerConstraintViolationException(ConstraintViolationException ex) {
        String message = null;
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            message = constraintViolation.getMessage();
        }
        return ApiResult.createFail(message);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiResult<Void>> handlerAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResult.createFail(ex).getBody());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResult<Void>> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException) {
            String value = (String) ((InvalidFormatException) ex.getCause()).getValue();
            return ApiResult.createFail("데이터 형식이 맞지 않습니다. 데이터 형식을 확인해주세요. 입력한 값: " + value);
        }

        return ApiResult.createFail("데이터 형식이 맞지 않습니다. 데이터 형식을 확인해주세요.");
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResult<Void>> handlerIllegalArgumentException(IllegalArgumentException ex) {
        return ApiResult.createFail(ex);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ApiResult<Void>> handlerNoSuchElementException(NoSuchElementException ex) {
        return ApiResult.createFail(ex);
    }

    /**
     * 메소드의 파라미터 타입이 잘못된 경우
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResult<Void>> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ApiResult.createFail(ex);
    }

    /**
     * validation 실패
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResult<Map<String, String>>> handlerBindException(MethodArgumentNotValidException ex) {
        return ApiResult.createValidationFail(ex.getBindingResult());
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    public ResponseEntity<ApiResult<Map<String, String>>> handlerMissingServletRequestPartException(MissingServletRequestPartException ex) {
        Map<String, String> errors = Map.of(ex.getRequestPartName(), "필수 값을 입력해주세요.");
        return ApiResult.createValidationFail(errors);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ApiResult<Map<String, String>>> handlerMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        Map<String, String> errors = Map.of(ex.getParameterName(), "필수 값을 입력해주세요.");
        return ApiResult.createValidationFail(errors);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiResult<Void>> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ApiResult.createFail().getBody(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<ApiResult<Void>> handleExpiredJwtException(ExpiredJwtException ex) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:ssssss");
        String message = "토큰이 만료되었습니다.(만료 시간: %s, 현재 시간: %s)\n다시 로그인 해주세요.".formatted(dateFormat.format(ex.getClaims().getExpiration()), LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResult.createFail(message).getBody());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResult<Void>> handleJwtException(JwtException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResult.createFail(ex.getMessage()).getBody());
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ApiResult<Void>> handlerCustomException(CustomException ex) {
        return ApiResult.createFail(ex, ex.getHttpStatus());
    }

    /**
     * 위에서 처리하지 못한 모든 예외 처리
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResult<?>> handlerException(Exception ex) {
        log.error("{}", ex);
        return ApiResult.createServerError("예상치 못한 에러가 발생했습니다.\n 관리자에게 문의해주세요.");
    }
}
