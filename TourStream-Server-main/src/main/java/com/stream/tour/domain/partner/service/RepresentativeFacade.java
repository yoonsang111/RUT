package com.stream.tour.domain.partner.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RepresentativeFacade {

    private final RepresentativeService representativeService;

    private final PartnerService partnerService;

    // 대표자 생성
//    public RepresentativeResponse saveRepresentative(RepresentativeRequest request){
//        Long representativeId = representativeService.saveRepresentative(
//                createRepresentative(request)
//        );
//    }
}
