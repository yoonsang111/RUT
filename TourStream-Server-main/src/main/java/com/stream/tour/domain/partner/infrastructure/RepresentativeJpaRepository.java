package com.stream.tour.domain.partner.infrastructure;

import com.stream.tour.domain.partner.infrastructure.entity.RepresentativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepresentativeJpaRepository extends JpaRepository<RepresentativeEntity, Long> {
    List<RepresentativeEntity> findByPartner_Id(Long partnerId);
}
