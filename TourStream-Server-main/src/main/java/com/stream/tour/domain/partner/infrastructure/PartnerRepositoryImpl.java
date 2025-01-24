package com.stream.tour.domain.partner.infrastructure;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.service.port.PartnerRepository;
import com.stream.tour.global.exception.custom.children.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PartnerRepositoryImpl implements PartnerRepository {

    private final PartnerJpaRepository partnerJpaRepository;

    @Override
    public Partner getById(Long partnerId) {
        return findById(partnerId).orElseThrow(() -> new ResourceNotFoundException("Partner", partnerId));
    }

    @Override
    public Optional<Partner> findById(Long partnerId) {
        return partnerJpaRepository.findById(partnerId).map(PartnerEntity::toModel);
    }

    @Override
    public Partner getByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Partner", email));
    }

    @Override
    public Optional<Partner> findByEmail(String email) {
        return partnerJpaRepository.findByEmail(email).map(PartnerEntity::toModel);

    }

    @Override
    public Partner save(Partner partner) {
        return partnerJpaRepository.save(PartnerEntity.from(partner)).toModel();
    }


    @Override
    public Partner findEmailByCorporateRegistrationNumberAndName(String corporateRegistrationNumber, String name) {
        return partnerJpaRepository.findEmailByCorporateRegistrationNumberAndName(corporateRegistrationNumber, name)
                .map(PartnerEntity::toModel)
                .orElseThrow(() -> new EntityNotFoundException("파트너를 찾을 수 없음"));
    }

    @Override
    public Partner findPasswordByEmailAndCorporateRegistrationNumberAndName(String email, String corporateRegistrationNumber, String name) {
        return partnerJpaRepository.findPasswordByEmailAndCorporateRegistrationNumberAndName(email, corporateRegistrationNumber, name)
                .map(PartnerEntity::toModel)
                .orElseThrow(() -> new EntityNotFoundException("파트너를 찾을 수 없음"));
    }
}
