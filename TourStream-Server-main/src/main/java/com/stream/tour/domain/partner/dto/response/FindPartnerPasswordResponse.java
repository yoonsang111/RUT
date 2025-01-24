package com.stream.tour.domain.partner.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPartnerPasswordResponse {

    private String message;

    private String password;

    public FindPartnerPasswordResponse(String message) {
        this.message = message;
    }
}
