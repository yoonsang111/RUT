package com.stream.tour.domain.partner.service;

import com.stream.tour.domain.partner.domain.Representative;
import com.stream.tour.domain.partner.infrastructure.entity.RepresentativeEntity;

import java.util.List;

public interface RepresentativeService {
    List<Representative> findByPartnerId(Long partnerId);
}
