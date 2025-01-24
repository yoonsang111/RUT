package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;

public class TokenNotFoundException extends CustomException {

    public TokenNotFoundException(String dataSource, Long parnterId) {
        super(dataSource + " Token Not Found. partnerId: " + parnterId);
    }
}
