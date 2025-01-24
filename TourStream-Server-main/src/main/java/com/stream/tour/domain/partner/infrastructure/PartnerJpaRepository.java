package com.stream.tour.domain.partner.infrastructure;

import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerJpaRepository extends JpaRepository<PartnerEntity, Long> {

    Optional<PartnerEntity> findByEmail(String email);

    Optional<PartnerEntity> findEmailByCorporateRegistrationNumberAndName(String corporateRegistrationNumber, String name);

    Optional<PartnerEntity> findPasswordByEmailAndCorporateRegistrationNumberAndName(String email, String corporateRegistrationNumber, String name);
}
