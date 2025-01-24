package com.stream.tour.global.jwt;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.service.PartnerService;
import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.children.TokenException;
import com.stream.tour.global.exception.custom.children.TokenNotFoundException;
import com.stream.tour.global.jwt.entity.JwtEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtRepository jwtRepository;
    private final JwtProvider jwtProvider;
    private final PartnerService partnerService;

    public JwtEntity findByAccessToken(String accessToken) {
        return jwtRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new TokenException(ErrorMessage.INVALID_ACCESS_TOKEN));
    }

    public JwtEntity findByPartnerId(Long partnerId) {
        return jwtRepository.findByPartnerId(partnerId)
                .orElseThrow(() -> new TokenNotFoundException("Jwt", partnerId));
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void deleteById(Long tokenId) {
        jwtRepository.deleteById(tokenId);
    }

    public JwtEntity save(JwtEntity jwtEntity) {
        return jwtRepository.save(jwtEntity);
    }

    public String generateNewAccessToken(HttpServletRequest request) {
        JwtEntity jwtEntity = findByAccessToken(jwtProvider.getTokenFrom(request));
        Partner partner = partnerService.getById(jwtEntity.getPartnerId());
        if (!jwtProvider.isTokenValid(jwtEntity.getRefreshToken(), partner)) {
            throw new TokenException(ErrorMessage.INVALID_REFRESH_TOKEN);
        }

        // 기존에 등록된 토큰 삭제
        this.deleteById(jwtEntity.getId());

        String newAccessToken = jwtProvider.generateToken(Map.of("partnerId", partner.getId()), partner);
        jwtEntity.updateAccessToken(newAccessToken);
        jwtRepository.save(jwtEntity); // 새로운 토큰으로 업데이트 - redis hash는 더티체킹안되는 것 같음
        return newAccessToken;
    }

    @Transactional
    public void deleteByAccessToken(String accessToken) {
        JwtEntity jwtEntity = jwtRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new TokenException(ErrorMessage.ACCESS_TOKEN_NOT_FOUND));
        jwtRepository.delete(jwtEntity);
    }
}
