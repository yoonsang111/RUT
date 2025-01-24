package com.stream.tour.domain.auth.service;

import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.domain.auth.dto.AccessTokenResponse;
import com.stream.tour.domain.auth.dto.LoginRequest;
import com.stream.tour.domain.partner.infrastructure.PartnerJpaRepository;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.global.jwt.JwtRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@DisplayName("로그인 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class AuthFacadeTest {

    @Mock private PasswordEncoder encoder;
    @Mock private JwtRepository tokenRepository;
    @Mock private PartnerJpaRepository partnerJpaRepository;
    @Mock private AuthService authService;


    @DisplayName("로그인에 실패한다 - 해당 이메일에 일치하는 파트너 존재하지 않음")
    @Test
    void failLogin1() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("adfadf@dfakdf2", "1111");

        given(partnerJpaRepository.findByEmail(loginRequest.getEmail())).willThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            authService.login(loginRequest);
        });
    }

    @DisplayName("로그인에 실패한다 - 비밀번호 불일치")
    @Test
    void failLogin2() throws Exception {

        // given
        LoginRequest loginRequest = new LoginRequest("email@email.com", "1111");
        PartnerEntity partner = PartnerTestUtils.createPartner();

        given(partnerJpaRepository.findByEmail(loginRequest.getEmail())).willReturn(Optional.ofNullable(partner));
        given(encoder.matches(eq("1111"), anyString())).willReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            authService.login(loginRequest);
        });
    }



    @DisplayName("로그인에 성공한다")
    @Test
    void successLogin() throws Exception {

        // Given
        LoginRequest loginRequest = new LoginRequest("email@email.com", "password");

        // Mock 파트너 객체 생성
        PartnerEntity partner = PartnerTestUtils.createPartner();

        // authService.login 메서드 호출 시 설정할 리턴값 설정
        AccessTokenResponse expectedResponse = AccessTokenResponse.builder()
                .accessToken("accessToken")
                .build();
//        given(authService.login(eq(loginRequest), any(PartnerEntity.class))).willReturn(expectedResponse);

        // partnerRepository.findByEmail 메서드의 반환값 설정;
        given(partnerJpaRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(partner));

        // encoder.matches에 대한 설정 (encoder.matches 호출 결과 설정)
        given(encoder.matches(eq(loginRequest.getPassword()), anyString())).willReturn(true);

        // When
        AccessTokenResponse actualResponse = authService.login(loginRequest);

        // Then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @DisplayName("로그아웃에 성공한다")
    @Test
    void successLogout() {
    }

}