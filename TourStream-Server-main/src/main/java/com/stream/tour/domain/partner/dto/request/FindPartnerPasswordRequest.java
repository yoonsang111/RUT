package com.stream.tour.domain.partner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindPartnerPasswordRequest {

    private String corporateRegistrationNumber;

    private String name;

    private String email;
}
