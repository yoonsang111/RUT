package com.stream.tour.domain.naver.naver.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static lombok.AccessLevel.PRIVATE;

@Builder
@AllArgsConstructor(access = PRIVATE)
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NaverOAuthRequest {
    private final String clientId;
    private final long timestamp;
    private final String clientSecretSign;
    private static final String grantType = "client_credentials";
    private final Type type;
    private String accountId; // type이 SELLER인 경우 입력해야 하는 판매자 ID 혹은 판매자 UID

    public static MultiValueMap<String, Object> toMultiValueMap(ObjectMapper objectMapper, String clientId, long timestamp, String clientSecretSign, Type type, String accountId) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();

        NaverOAuthRequest request = NaverOAuthRequest.builder()
                .clientId(clientId)
                .timestamp(timestamp)
                .clientSecretSign(clientSecretSign)
                .type(type)
                .accountId(accountId)
                .build();

        multiValueMap.setAll(objectMapper.convertValue(request, new TypeReference<>() {}));
        return multiValueMap;
    }

    /**
     * 인증 토큰 발급 타입.
     * SELF인 경우 자기 자신의 리소스, SELLER인 경우 관련 판매자의 리소스에 대한 발급.
     */
    public enum Type {
        SELLER,
        SELF;
    }
}
