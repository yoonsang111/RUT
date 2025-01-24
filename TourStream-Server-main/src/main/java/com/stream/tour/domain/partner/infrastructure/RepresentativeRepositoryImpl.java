package com.stream.tour.domain.partner.infrastructure;

import com.stream.tour.domain.partner.domain.Representative;
import com.stream.tour.domain.partner.infrastructure.entity.RepresentativeEntity;
import com.stream.tour.domain.partner.service.port.RepresentativeRepository;
import com.stream.tour.global.exception.custom.children.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RepresentativeRepositoryImpl implements RepresentativeRepository {

    private final RepresentativeJpaRepository representativeJpaRepository;

    @Override
    public List<Representative> findByPartnerId(Long partnerId) {
        return representativeJpaRepository.findByPartner_Id(partnerId)
                .stream().map(RepresentativeEntity::toModel).toList();
    }
}
