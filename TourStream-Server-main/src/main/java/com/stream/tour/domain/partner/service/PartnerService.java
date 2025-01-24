package com.stream.tour.domain.partner.service;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.dto.request.FindPartnerPasswordRequest;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import com.stream.tour.domain.partner.dto.response.FindPartnerPasswordResponse;

public interface PartnerService {
    Partner getById(Long id);

    Long savePartner(PartnerRequest.Save partner);

    Long updatePartner(PartnerRequest.Update request);

    FindPartnerPasswordResponse changePassword(Partner partner);
    Partner findEmailByCorporateRegistrationNumberAndName(String corporateRegistrationNumber, String name);

    Partner findPasswordByEmailAndCorporateRegistrationNumberAndName(String email, String corporateRegistrationNumber, String name);
}
