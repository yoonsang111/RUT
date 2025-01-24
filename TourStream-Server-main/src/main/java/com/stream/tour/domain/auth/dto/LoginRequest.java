package com.stream.tour.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginRequest", description = "로그인 요청")
public class LoginRequest {

    @Schema(name = "email", description = "로그인 이메일", example = "test@gmail.com")
    private String email;

    @Schema(name = "password", description = "로그인 비밀번호", example = "1111")
    private String password;
}
