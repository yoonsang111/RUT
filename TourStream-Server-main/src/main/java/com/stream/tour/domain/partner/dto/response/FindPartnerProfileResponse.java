package com.stream.tour.domain.partner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FindPartnerProfileResponse {

    private Long partnerId;

    private String name;

    private String phoneNumber;

    private String email;

    private String corporateRegistrationNumber;

    private String customerServiceContact;

}
