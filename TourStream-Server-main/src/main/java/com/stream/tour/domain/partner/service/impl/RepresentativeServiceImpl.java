package com.stream.tour.domain.partner.service.impl;

import com.stream.tour.domain.partner.domain.Representative;
import com.stream.tour.domain.partner.infrastructure.entity.RepresentativeEntity;
import com.stream.tour.domain.partner.service.RepresentativeService;
import com.stream.tour.domain.partner.service.port.RepresentativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RepresentativeServiceImpl implements RepresentativeService {

    private final RepresentativeRepository representativeRepository;

    @Override
    public List<Representative> findByPartnerId(Long partnerId) {
        return representativeRepository.findByPartnerId(partnerId);
    }
}
