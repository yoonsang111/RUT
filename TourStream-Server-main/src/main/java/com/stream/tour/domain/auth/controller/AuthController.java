package com.stream.tour.domain.auth.controller;

import com.stream.tour.domain.auth.dto.LoginRequest;
import com.stream.tour.domain.auth.dto.AccessTokenResponse;
import com.stream.tour.domain.auth.service.AuthService;
import com.stream.tour.global.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "로그인을 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
            @ApiResponse(responseCode = "500", description = "로그인 실패", content = @Content(schema = @Schema(implementation = ApiResult.class)))
    })
    @PostMapping("/login")
    public ApiResult<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return ApiResult.createSuccessV2(authService.login(loginRequest));
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
            @ApiResponse(responseCode = "500", description = "로그아웃 실패", content = @Content(schema = @Schema(implementation = ApiResult.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResult<Void>> logout(HttpServletRequest request) {
        return authService.logout(request);
    }

    @GetMapping("/refresh")
    public ApiResult<AccessTokenResponse> refresh() {
        return ApiResult.createSuccessV2(authService.refresh());
    }
}
