package com.stream.tour.global.cookie.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CookieService {

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public void addRefreshToken(String refreshToken) {
        Cookie cookie = new Cookie("refresh-token", refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (refreshTokenExpiration / 1000)); // 초 단위

        httpServletResponse.addCookie(cookie);
    }

    public Optional<String> getRefreshToken() {
        return findRefreshToken();
    }

    private Optional<String> findRefreshToken() {
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refresh-token"))
                .map(Cookie::getValue)
                .findAny();
    }

    public void deleteRefreshToken() {
        findRefreshToken().ifPresent(refreshToken -> {
            Cookie cookie = new Cookie("refresh-token", refreshToken);
            cookie.setMaxAge(0);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
        });
    }





}
