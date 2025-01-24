package com.stream.tour.domain.auth.service.impl;

import com.stream.tour.domain.auth.dto.LoginRequest;
import com.stream.tour.domain.auth.dto.AccessTokenResponse;
import com.stream.tour.domain.auth.service.AuthService;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.service.port.PartnerRepository;
import com.stream.tour.global.cookie.service.CookieService;
import com.stream.tour.global.dto.ApiResult;
import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.children.NotRegisteredPartnerException;
import com.stream.tour.global.exception.custom.children.UnauthorizedException;
import com.stream.tour.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookieService cookieService;

    @Transactional
    @Override
    public AccessTokenResponse login(LoginRequest loginRequest) {
        Partner foundPartner = partnerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotRegisteredPartnerException(loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), foundPartner.getPassword())) {
            throw new IllegalArgumentException(ErrorMessage.SECURITY_ERROR.getMessage());
        }

        String accessToken = jwtProvider.generateToken(Map.of("partnerId", foundPartner.getId()), foundPartner);
        String refreshToken = jwtProvider.generateRefreshToken(foundPartner);

        foundPartner = foundPartner.updateRefreshToken(refreshToken);
        partnerRepository.save(foundPartner);
        cookieService.addRefreshToken(refreshToken);

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtProvider.getExpiresIn())
                .build();
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResult<Void>> logout(HttpServletRequest httpServletRequest) {
        String refreshToken = cookieService.getRefreshToken().orElse(null);

        // client에 refreshToken이 없는 경우
        if (refreshToken == null) {
            return ApiResult.createSuccessWithNoContent();
        }

        String email = jwtProvider.extractUsernameFromRefreshToken(refreshToken);
        Partner partner = partnerRepository.findByEmail(email)
                .orElseThrow(() -> new NotRegisteredPartnerException(email));

        partner = partner.updateRefreshToken(null);
        partnerRepository.save(partner);
        cookieService.deleteRefreshToken();

        return ApiResult.createSuccessWithNoContent();
    }

    @Override
    public AccessTokenResponse refresh() {
        String refreshToken = cookieService.getRefreshToken()
                .orElseThrow(UnauthorizedException::new);

        String email = jwtProvider.extractUsernameFromRefreshToken(refreshToken);
        Partner partner = partnerRepository.findByEmail(email)
                .orElseThrow(() -> new NotRegisteredPartnerException(email));

        String accessToken = jwtProvider.generateToken(Map.of("partnerId", partner.getId()), partner);

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtProvider.getExpiresIn())
                .build();
    }

    @Override
    public Partner getPartner() {
        PartnerEntity partnerEntity = (PartnerEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Partner.from(partnerEntity);
    }

    @Override
    public Long getPartnerId() {
        return this.getPartner().getId();
    }
}
