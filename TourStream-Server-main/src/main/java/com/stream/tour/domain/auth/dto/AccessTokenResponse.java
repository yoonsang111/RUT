package com.stream.tour.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "로그인 응답")
public record AccessTokenResponse(
        @Schema(name = "accessToken", description = "액세스 토큰", example = "acg2ijkzkjkkljknqndf")
        String accessToken,

        @Schema(name = "tokenType", description = "액세스 토큰 타입", example = "Bearer")
        String tokenType,

        @Schema(name = "expiresIn", description = "만료 시간", example = "899")
        Long expiresIn
) {

        @Builder
        public AccessTokenResponse {
        }
}