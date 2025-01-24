package com.stream.tour.domain.partner.service.port;

import com.stream.tour.domain.partner.domain.Representative;

import java.util.List;

public interface RepresentativeRepository {
    List<Representative> findByPartnerId(Long partnerId);
}
