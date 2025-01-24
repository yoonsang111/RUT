package com.stream.tour.global.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Getter
public class JwtFactory {

    private String subject = "test@email.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = Collections.emptyMap();

    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration, Map<String, Object> claims) {
        if (subject != null) {
            this.subject = subject;
        }
        if (issuedAt != null) {
            this.issuedAt = issuedAt;
        }
        if (expiration != null) {
            this.expiration = expiration;
        }
        if (claims != null) {
            this.claims = claims;
        }
    }

    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    /**
     * jjwt 라이브러리를 사용해 JWT 토큰을 생성한다.
     */
    public String generateToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}
