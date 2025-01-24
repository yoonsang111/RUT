package com.stream.tour.domain.partner.service;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.domain.Representative;
import com.stream.tour.domain.partner.dto.request.FindPartnerEmailRequest;
import com.stream.tour.domain.partner.dto.request.FindPartnerPasswordRequest;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import com.stream.tour.domain.partner.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PartnerFacade {

    private final PartnerService partnerService;
    private final PasswordEncoder passwordEncoder;

    // 파트너 생성
    public PartnerResponse.Save savePartner(PartnerRequest.Save request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Long partnerId = partnerService.savePartner(request);

        return new PartnerResponse.Save(partnerId);
    }

    // 파트너 조회
    public FindPartnerResponse findPartner(Long partnerId) {
        Partner partner = partnerService.getById(partnerId);
        return new FindPartnerResponse(partner);
    }

    // 파트너 수정
    public PartnerResponse.Update updatePartner(PartnerRequest.Update request) {
        Long partnerId = partnerService.updatePartner(request);
        return new PartnerResponse.Update(partnerId);
    }

    // 이메일 찾기
    public FindPartnerEmailResponse findPartnerEmail(FindPartnerEmailRequest request) {
        String corporateRegistrationNumber = request.getCorporateRegistrationNumber();
        String name = request.getName();

        // 데이터베이스에서 파트너 찾기
        Partner partner = partnerService.findEmailByCorporateRegistrationNumberAndName(corporateRegistrationNumber, name);

        // 파트너를 찾았을 때 FindPartnerIdResponse 객체를 생성하여 반환
        String email = partner.getEmail();
        FindPartnerEmailResponse response = new FindPartnerEmailResponse();
        response.setEmail(email);
        return response;
    }

    // 비밀번호 찾고 임시비밀번호 이메일 전송
    public FindPartnerPasswordResponse findPartnerPassword(FindPartnerPasswordRequest request) {
        Partner partner = partnerService.findPasswordByEmailAndCorporateRegistrationNumberAndName(
                request.getEmail(),
                request.getCorporateRegistrationNumber(),
                request.getName()
        );

//        List<Representative> represent = representativeService.findByPartnerId(partner.getId());
//        partner.getRepresentatives().addAll(represent);

        return partnerService.changePassword(partner);
    }

    public FindPartnerProfileResponse findPartnerProfile(Long partnerId) {
        Partner partner = partnerService.getById(partnerId);
        return FindPartnerProfileResponse.builder()
                .partnerId(partner.getId())
                .name(partner.getName())
                .phoneNumber(partner.getPhoneNumber())
                .email(partner.getEmail())
                .corporateRegistrationNumber(partner.getCorporateRegistrationNumber())
                .customerServiceContact(partner.getCustomerServiceContact())
                .build();
    }

}
