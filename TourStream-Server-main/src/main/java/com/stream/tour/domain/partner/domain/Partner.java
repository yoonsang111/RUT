package com.stream.tour.domain.partner.domain;

import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.reservations.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Partner {
    private Long id; // 파트너 아이디
    //    private List<Representative> representatives = new ArrayList<>();
//    private List<ProductEntity> products = new ArrayList<>();
    private String corporateRegistrationNumber; // 사업자 등록 번호
    private String name; // 사업자 명
    private String phoneNumber; // 휴대폰 번호
    private String email; // 이메일
    private String password; // 비밀번호
    private boolean passwordChanged; // 비밀번호 변경 여부
    private String customerServiceContact; // 고객 센터 연락처
    private LocalTime operationStartTime; // 운영 시작 시간
    private LocalTime operationEndTime; // 운영 종료 시간
    private String emergencyContact; // 비상 연락망
    private List<Representative> representatives = new ArrayList<>();
    private String refreshToken;

    public static Partner from(UserDetails userDetails) {
        PartnerEntity partnerEntity = (PartnerEntity) userDetails;

        return Partner.builder()
                .id(partnerEntity.getId())
                .corporateRegistrationNumber(partnerEntity.getCorporateRegistrationNumber())
                .name(partnerEntity.getName())
                .phoneNumber(partnerEntity.getPhoneNumber())
                .email(partnerEntity.getEmail())
                .password(partnerEntity.getPassword())
                .passwordChanged(partnerEntity.isPasswordChanged())
                .customerServiceContact(partnerEntity.getCustomerServiceContact())
                .operationStartTime(partnerEntity.getOperationStartTime())
                .operationEndTime(partnerEntity.getOperationEndTime())
                .emergencyContact(partnerEntity.getEmergencyContact())
                .refreshToken(partnerEntity.getRefreshToken())
                .build();
    }

    public boolean isMyProduct(Product product) {
        return Objects.equals(product.getPartner().getId(), product.getId());
    }

    public boolean isMyProduct(List<Product> products) {
        return products.stream().allMatch(product -> Objects.equals(product.getPartner().getId(), this.id));
    }

    public boolean isMyReservation(List<Reservation> reservations) {
        return reservations.stream()
                .mapToLong(reservation -> reservation.getOption().getProduct().getPartner().getId())
                .allMatch(partnerId -> partnerId == this.id);
    }

    public boolean isMyReservation(Reservation reservation) {
        return Objects.equals(reservation.getOption().getProduct().getPartner().getId(), this.id);
    }

    public Partner update(PartnerRequest.Update request) {
        return Partner.builder()
                .id(id)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(password)
                .corporateRegistrationNumber(request.getCorporateRegistrationNumber())
                .customerServiceContact(request.getCustomerServiceContact())
                .operationStartTime(request.getOperationStartTime())
                .operationEndTime(request.getOperationEndTime())
                .emergencyContact(request.getEmergencyContact())
                .representatives(request.getRepresentatives())
                .build();
    }

    public Partner changePassword(String newPassword) {
        return Partner.builder()
                .id(id)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .password(newPassword)
                .corporateRegistrationNumber(corporateRegistrationNumber)
                .customerServiceContact(customerServiceContact)
                .operationStartTime(operationStartTime)
                .operationEndTime(operationEndTime)
                .emergencyContact(emergencyContact)
                .representatives(representatives)
                .build();
    }

    public static Partner from(PartnerRequest.Save request) {
        return Partner.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(request.getPassword())
                .corporateRegistrationNumber(request.getCorporateRegistrationNumber())
                .customerServiceContact(request.getCustomerServiceContact())
                .operationStartTime(request.getOperationStartTime())
                .operationEndTime(request.getOperationEndTime())
                .emergencyContact(request.getEmergencyContact())
                .representatives(request.getRepresentatives())
                .build();
    }


    public Partner updateRefreshToken(String refreshToken) {
        return Partner.builder()
                .id(id)
                .corporateRegistrationNumber(corporateRegistrationNumber)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .password(password)
                .passwordChanged(passwordChanged)
                .customerServiceContact(customerServiceContact)
                .operationStartTime(operationStartTime)
                .operationEndTime(operationEndTime)
                .emergencyContact(emergencyContact)
                .refreshToken(refreshToken)
                .build();
    }
}
