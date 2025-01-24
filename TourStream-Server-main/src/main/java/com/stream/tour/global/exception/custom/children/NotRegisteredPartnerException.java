package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;

public class NotRegisteredPartnerException extends CustomException {

    public NotRegisteredPartnerException(String email) {
        super("회원가입 되지 않은 파트너입니다. email: " + email);
    }
}
