package com.stream.tour.domain.auth.service;

import com.stream.tour.domain.auth.dto.LoginRequest;
import com.stream.tour.domain.auth.dto.AccessTokenResponse;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.global.dto.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
   AccessTokenResponse login(LoginRequest loginRequest) throws Exception;

   ResponseEntity<ApiResult<Void>> logout(HttpServletRequest request);

   Partner getPartner();

   Long getPartnerId();

   AccessTokenResponse refresh();
}
