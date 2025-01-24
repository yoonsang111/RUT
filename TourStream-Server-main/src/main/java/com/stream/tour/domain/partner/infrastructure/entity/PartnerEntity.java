package com.stream.tour.domain.partner.infrastructure.entity;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import com.stream.tour.domain.partner.enums.Role;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name ="partner")
public class PartnerEntity extends BaseEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("파트너 아이디")
    @Column(name = "partner_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<RepresentativeEntity> representativeEntities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();

    @Comment("사업자 등록 번호")
    @Column(nullable = false)
    private String corporateRegistrationNumber;

    @Comment("사업자 명")
    @Column(nullable = false, length = 50)
    private String name;

    @Comment("휴대폰 번호")
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Comment("이메일")
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Comment("비밀번호")
    @Column(nullable = false, length = 255)
    private String password;

    @Comment("비밀번호 변경 여부")
    @Column(columnDefinition = "TINYINT", length = 1, nullable = false)
    private boolean passwordChanged;

    @Comment("고객 센터 연락처")
    @Column(nullable = false, length = 50)
    private String customerServiceContact;

    @Comment("운영 시작 시간")
    @Column(nullable = false)
    private LocalTime operationStartTime;

    @Comment("운영 종료 시간")
    @Column(nullable = false)
    private LocalTime operationEndTime;

    @Comment("비상 연락망")
    @Column(nullable = false)
    private String emergencyContact;
    private String refreshToken;

    public Partner toModel() {
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
//                .representatives(representativeEntities.stream().map(RepresentativeEntity::toModel).toList())
                .representatives(new ArrayList<>())
                .build();
    }

    //==생성 메서드==//
    public static PartnerEntity from(Partner partner) {
        PartnerEntity partnerEntity = PartnerEntity.builder()
                .id(partner.getId())
                .products(new ArrayList<>())
                .name(partner.getName())
                .phoneNumber(partner.getPhoneNumber())
                .email(partner.getEmail())
                .password(partner.getPassword())
                .corporateRegistrationNumber(partner.getCorporateRegistrationNumber())
                .customerServiceContact(partner.getCustomerServiceContact())
                .operationStartTime(partner.getOperationStartTime())
                .operationEndTime(partner.getOperationEndTime())
                .emergencyContact(partner.getEmergencyContact())
                .representativeEntities(RepresentativeEntity.from(partner.getRepresentatives()))
                .build();

        partnerEntity.representativeEntities.forEach(representative -> representative.addPartner(partnerEntity));

        return partnerEntity;
    }


    //==수정 메서드==//
    public void updatePartner(PartnerRequest.Update request) {
        this.name = request.getName();
        this.phoneNumber = request.getPhoneNumber();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.customerServiceContact = request.getCustomerServiceContact();
        this.operationStartTime = request.getOperationStartTime();
        this.operationEndTime = request.getOperationEndTime();
        this.emergencyContact = request.getEmergencyContact();
//        this.representatives = request.getRepresentatives();
    }

    //==비즈니스 로직==//
    public boolean isMyProduct(ProductEntity product) {
        return this.products.contains(product);
    }

    public boolean isMyProduct(List<Product> products) {
        return products.stream()
                .filter(product -> !this.products.contains(product))
                .toList().isEmpty();
    }

    public boolean isMyReservation(List<ReservationEntity> reservationEntities) {
        Set<Long> reservationIds = getMyReservationIds();

        return reservationEntities.stream()
                .allMatch(reservation -> reservationIds.contains(reservation.getId()));
    }

    public boolean isMyReservation(Long reservationId) {
        Set<Long> reservationIds = getMyReservationIds();

        return reservationIds.contains(reservationId);
    }

    public Set<Long> getMyReservationIds() {
        return this.products.stream()
                .flatMap(product -> product.getOptions().stream())
                .flatMap(option -> option.getReservationEntities().stream())
                .map(ReservationEntity::getId)
                .collect(Collectors.toSet());
    }

    public void changeTmpPassword(String tmpPassword) {
        this.password = tmpPassword;
    }

    //==UserDetails 메서드==//
    /**
     * 사용자의 권한을 가져오는 메서드
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.PARTNER.toString()));
    }

    /**
     * 사용자의 id를 가져오는 메서드 (고유한 값)
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * 사용자의 계졍 만료 여부를 가져오는 메서드
     */
    @Override
    public boolean isAccountNonExpired() {
        // IF NEEDED 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않음
    }

    /**
     * 사용자의 계정 잠금 여부를 가져오는 메서드
     */
    @Override
    public boolean isAccountNonLocked() {
        // IF NEEDED 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않음
    }

    /**
     * 사용자의 비밀번호 만료 여부를 가져오는 메서드
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        // IF NEEDED 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않음
    }

    /**
     * 사용자의 활성화 여부를 가져오는 메서드
     */
    @Override
    public boolean isEnabled() {
        // IF NEEDED 계졍이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}