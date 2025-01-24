package com.stream.tour.domain.partner.service.port;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;

import java.util.Optional;

public interface PartnerRepository {
    Partner getById(Long partnerId);
    Optional<Partner> findById(Long partnerId);
    Optional<Partner> findByEmail(String email);
    Partner getByEmail(String email);
    Partner save(Partner partner);
    Partner findEmailByCorporateRegistrationNumberAndName(String corporateRegistrationNumber, String name);
    Partner findPasswordByEmailAndCorporateRegistrationNumberAndName(String email, String corporateRegistrationNumber, String name);

}
