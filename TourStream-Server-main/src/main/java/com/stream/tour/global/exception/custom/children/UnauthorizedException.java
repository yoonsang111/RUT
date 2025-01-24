package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
