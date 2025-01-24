package com.stream.tour.global.jwt;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.children.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtProvider {

    @Value("${jwt.access-token.secret-key}")
    private String accessTokenSecretKey;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token.secret-key}")
    private String refreshTokenSecretKey;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Value("${jwt.issuer}")
    private String issuer;

    public String generateToken(Partner partner) {
        return generateToken(new HashMap<>(), partner);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            Partner partner
    ) {
        return buildToken(extraClaims, partner, accessTokenExpiration, TokenType.ACCESS);
    }

    public String generateRefreshToken(Partner partner) {
        return buildToken(new HashMap<>(), partner, refreshTokenExpiration, TokenType.REFRESH);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            Partner partner,
            long expiration,
            TokenType tokenType
    ) {
        var now = System.currentTimeMillis();
        Key signInKey = getSignInKey(tokenType);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(partner.getEmail()) // our Spring Security's email equals email
                .setIssuer(issuer)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
                .signWith(signInKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, Partner partner) {
        final String email = extractUsername(token);
        return (email.equals(partner.getEmail())) && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, TokenType.ACCESS);
        return claimsResolver.apply(claims);
    }

    public <T> T extractClaimFromRefresh(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, TokenType.REFRESH);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType tokenType) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(tokenType.equals(TokenType.ACCESS) ? TokenType.ACCESS : TokenType.REFRESH))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * our Spring Security's email equals email
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUsernameFromRefreshToken(String token) {
            return extractClaimFromRefresh(token, Claims::getSubject);
        }

    public Long extractPartnerId(String token) {
        return extractClaim(token, claims -> claims.get("partnerId", Long.class));
    }

    private Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

    private Key getSignInKey(TokenType tokenType) {
        var secretKey = tokenType == TokenType.ACCESS ? accessTokenSecretKey : refreshTokenSecretKey;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            throw new TokenException(ErrorMessage.INVALID_ACCESS_TOKEN);
        }
    }

    public String getTokenFrom(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return getToken(header);
    }

    public long getExpiresIn() {
        return accessTokenExpiration / 1000 - 1;
    }


    enum TokenType {
        ACCESS, REFRESH
    }
}
