package com.stream.tour.domain.partner.dto.response;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.domain.Representative;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindPartnerResponse {

    private List<Representative> representatives;

    private String name;

    private String phoneNumber;

    private String email;

    private String password;

    private String corporateRegistrationNumber;

    private String customerServiceContact;

    private LocalTime operationStartTime;

    private LocalTime operationEndTime;

    private String emergencyContact;

    public FindPartnerResponse(Partner partner) {
        // FIXME partner.getRepresentatives() lazy loading 수정
//        this.representatives = partner.getRepresentatives()
//                .stream()
//                .map(Representative::toSaveRepresentativesRequest)
//                .collect(Collectors.toList());
        this.name = partner.getName();
        this.phoneNumber = partner.getPhoneNumber();
        this.email = partner.getEmail();
        this.password = partner.getPassword();
        this.corporateRegistrationNumber = partner.getCorporateRegistrationNumber();
        this.customerServiceContact = partner.getCustomerServiceContact();
        this.operationStartTime = partner.getOperationStartTime();
        this.operationEndTime = partner.getOperationEndTime();
        this.emergencyContact = partner.getEmergencyContact();
    }
}
