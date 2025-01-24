package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.CustomException;
import org.springframework.http.HttpStatus;

public class AccessDeniedException extends CustomException {

    public AccessDeniedException() {
        super(ErrorMessage.ACCESS_DENIED.getMessage(), HttpStatus.FORBIDDEN);
    }
}
