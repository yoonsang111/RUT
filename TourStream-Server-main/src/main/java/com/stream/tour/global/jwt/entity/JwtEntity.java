package com.stream.tour.global.jwt.entity;

import com.stream.tour.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "jwt")
@Entity
public class JwtEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private Long partnerId;

    @Column(nullable = false, unique = true)
    private String accessToken;


    @Column(nullable = false)
    private String refreshToken;

    //===생성 메서드===//
    public static JwtEntity createRefreshToken(Long partnerId, String refreshToken) {
        return JwtEntity.builder()
                .partnerId(partnerId)
                .refreshToken(refreshToken)
                .build();
    }

    public static JwtEntity createToken(Long partnerId, String accessToken, String refreshToken) {
        return JwtEntity.builder()
                .partnerId(partnerId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //===비즈니스 로직===//
    public void updateAccessToken(String newAccessToken) {
        this.accessToken = newAccessToken;
    }
}
