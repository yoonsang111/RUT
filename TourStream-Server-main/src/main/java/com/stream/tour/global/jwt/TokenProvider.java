package com.stream.tour.global.jwt;

import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.enums.Role;
import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.children.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Deprecated
@Slf4j
@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String PARTNER_ID = "partnerId";

    public String generateToken(PartnerEntity partner, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), partner);
    }

    /**
     * JWT 토큰 생성 메서드
     */
    private String makeToken(Date expiry, PartnerEntity partner) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)              // 헤더 type : JWT
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)                                          // 내용 iat : 현재 시간
                .setExpiration(expiry)                                     // 내용 exp : expiry
                .setSubject(partner.getEmail())
                .claim(PARTNER_ID, partner.getId())                 // 클레임 id : 파트너 id
                // 서명 : 시크릿키값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 시크릿 키 값으로 복호화
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 토큰 기반으로 인증 정보를 가져오는 메서드
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(Role.PARTNER.toString()));

        return new UsernamePasswordAuthenticationToken(new User(claims.getSubject(), "", authorities), "", authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parser() // 클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * 토큰 기반으로 유저 ID를 가져오는 메서드
     */
    public Long getPartnerId(String token) {
        Claims claims = getClaims(token);
        return claims.get(PARTNER_ID, Long.class);
    }

    public String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            // 접두사 제거
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        } else {
            throw new TokenException(ErrorMessage.INVALID_ACCESS_TOKEN);
        }
    }

    public String getAccessTokenFrom(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return getAccessToken(header);
    }
}
