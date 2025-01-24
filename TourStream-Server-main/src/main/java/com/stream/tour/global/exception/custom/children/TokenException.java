package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.CustomException;

public class TokenException extends CustomException {
    public TokenException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
