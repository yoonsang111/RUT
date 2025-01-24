package com.stream.tour.global.jwt;

import com.stream.tour.global.dto.ApiResult;
import com.stream.tour.global.jwt.domain.GenerateAccessTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "토큰 API", description = "토큰 API")
@RequiredArgsConstructor
@RestController
public class JwtController {
    private final JwtService jwtService;

    @Operation(summary = "access token 재발급", description = "만료된 accessToken으로 refreshToken을 찾아서 유효하면 새로운 accessToken을 발급한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 등록 성공"),
    })
    @PostMapping("/access-token")
    public ResponseEntity<ApiResult<GenerateAccessTokenResponse>> generateAccessToken(HttpServletRequest request) {
        String accessToken = jwtService.generateNewAccessToken(request);
        return ApiResult.createSuccess(new GenerateAccessTokenResponse(accessToken));
    }
}
