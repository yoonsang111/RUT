package com.stream.tour.global.jwt.annotation;

import com.stream.tour.global.exception.ErrorMessage;
import com.stream.tour.global.exception.custom.children.TokenException;
import com.stream.tour.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginPartnerArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtPartnerId.class); // JwtPartnerId 어노테이션이 붙어있는지 확인
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var authHeader = Optional.ofNullable(
                        webRequest.getNativeRequest(HttpServletRequest.class).getHeader("Authorization")
                )
                .orElseThrow(() -> new TokenException(ErrorMessage.AUTHORIZATION_HEADER_NOT_FOUND));
        final String jwt = authHeader.substring(7);
        final String email = jwtProvider.extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (!jwtProvider.isTokenValid(jwt, userDetails)) {
            throw new TokenException(ErrorMessage.INVALID_ACCESS_TOKEN);
        }
        return jwtProvider.extractPartnerId(jwt);
    }
}
