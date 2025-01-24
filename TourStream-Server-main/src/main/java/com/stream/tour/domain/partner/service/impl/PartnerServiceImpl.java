package com.stream.tour.domain.partner.service.impl;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import com.stream.tour.domain.partner.dto.response.FindPartnerPasswordResponse;
import com.stream.tour.domain.partner.infrastructure.PartnerJpaRepository;
import com.stream.tour.domain.partner.service.CertificationService;
import com.stream.tour.domain.partner.service.PartnerService;
import com.stream.tour.domain.partner.service.port.PartnerRepository;
import com.stream.tour.global.email.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final CertificationService certificationService;

    @Override
    public Partner getById(Long partnerId) {
        return partnerRepository.getById(partnerId);
    }

    @Override
    @Transactional
    public Long savePartner(PartnerRequest.Save request){
        Partner partner = Partner.from(request);
        Partner savedPartner = partnerRepository.save(partner);

        return savedPartner.getId();
    }

    @Override
    @Transactional
    public Long updatePartner(PartnerRequest.Update request){
        Partner partner = getById(request.getPartnerId());
        partner = partner.update(request);
        partner = partnerRepository.save(partner);
        return partner.getId();
    }

    @Transactional
    @Override
    public FindPartnerPasswordResponse changePassword(Partner partner) {
        // 임시 비밀번호 생성 및 메일 메시지 생성
        String newPassword = getTempPassword();
        EmailMessage emailMessage = createMailAndChangePassword(partner.getEmail(),newPassword);

        // 이메일 전송
        certificationService.send(emailMessage);

        // 임시 비밀번호를 데이터베이스에 저장
        partner = partner.changePassword(newPassword);
        partnerRepository.save(partner);

        return new FindPartnerPasswordResponse("임시 비밀번호가 이메일로 전송되었습니다.");
    }

    @Override
    @Transactional
    public Partner findEmailByCorporateRegistrationNumberAndName(String corporateRegistrationNumber, String name){
        return partnerRepository.findEmailByCorporateRegistrationNumberAndName(corporateRegistrationNumber, name);
    }

    @Override
    @Transactional
    public Partner findPasswordByEmailAndCorporateRegistrationNumberAndName(String email, String corporateRegistrationNumber, String name){
        return partnerRepository.findPasswordByEmailAndCorporateRegistrationNumberAndName(email, corporateRegistrationNumber, name);
    }

    private EmailMessage createMailAndChangePassword(String partnerEmail, String newPassword) {
        EmailMessage message = new EmailMessage();
        message.setTo(partnerEmail);
        message.setSubject("TourStream 임시 비밀번호 발급");
        message.setMessage("안녕하세요. TourStream 임시비밀번호 안내 관련 메일입니다." + "회원님의 임시 비밀번호는 " + newPassword + " 입니다."
                + " 로그인 후에 비밀번호를 변경해 주세요.");

        return message;
    }

    //랜덤함수로 임시비밀번호 구문 만들기
    private String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
