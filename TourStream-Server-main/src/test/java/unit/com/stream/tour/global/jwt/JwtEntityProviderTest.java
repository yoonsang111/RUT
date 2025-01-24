package com.stream.tour.global.jwt;

import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.infrastructure.PartnerJpaRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class JwtEntityProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private PartnerJpaRepository partnerJpaRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    public void generateToken() throws Exception {
        // given
        PartnerEntity partner = PartnerTestUtils.createPartner();
        partnerJpaRepository.save(partner);

        // when
        String token = tokenProvider.generateToken(partner, Duration.ofDays(14));

        // then
        Long partnerId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("partnerId", Long.class);

        assertThat(partnerId).isEqualTo(partner.getId());
    }

    @DisplayName("validateToken(): 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    public void invalidToken() throws Exception {
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .generateToken(jwtProperties);

        // when
        boolean isValid = tokenProvider.validateToken(token);

        // then
        assertThat(isValid).isFalse();
    }

    @DisplayName("validateToken(): 유효한 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    public void validToken() throws Exception {
        // given
        String token = JwtFactory.withDefaultValues().generateToken(jwtProperties);
        System.out.println("token = " + token);

        // when
        boolean isValid = tokenProvider.validateToken(token);

        // then
        assertThat(isValid).isTrue();
    }

    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    public void getAuthentication() throws Exception {
        // given
        String partnerEmail = "partner@email.com";
        String token = JwtFactory.builder()
                .subject(partnerEmail)
                .build()
                .generateToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(partnerEmail);
    }

    @DisplayName("getUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    public void getUserId() throws Exception {
        // given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("partnerId", userId))
                .build()
                .generateToken(jwtProperties);

        // when
        Long partnerIdByToken = tokenProvider.getPartnerId(token);

        // then
        assertThat(partnerIdByToken).isEqualTo(userId);
    }
}