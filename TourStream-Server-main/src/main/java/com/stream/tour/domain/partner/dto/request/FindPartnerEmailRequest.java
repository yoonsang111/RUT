package com.stream.tour.domain.partner.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FindPartnerEmailRequest {

    private String corporateRegistrationNumber;

    private String name;
}
