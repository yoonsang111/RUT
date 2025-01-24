package com.stream.tour.global.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
