package com.stream.tour.domain.partner.controller;

import com.stream.tour.domain.partner.dto.request.FindPartnerEmailRequest;
import com.stream.tour.domain.partner.dto.request.FindPartnerPasswordRequest;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import com.stream.tour.domain.partner.dto.response.*;
import com.stream.tour.global.dto.ApiResult;
import com.stream.tour.global.jwt.annotation.JwtPartnerId;
import com.stream.tour.domain.partner.service.PartnerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "파트너 API", description = "파트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/partners")
public class PartnerController {

    private final PartnerFacade partnerFacade;

    @Operation(summary = "파트너 등록", description = "파트너를 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @PostMapping
    public ResponseEntity<ApiResult<PartnerResponse.Save>> savePartner(@RequestBody @Validated PartnerRequest.Save request){
        return ApiResult.createSuccess(partnerFacade.savePartner(request));
    }

    @Operation(summary = "파트너 조회", description = "파트너를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @GetMapping("/{partnerId}")
    public ResponseEntity<ApiResult<FindPartnerResponse>> findPartner(@PathVariable Long partnerId){
        return ApiResult.createSuccess(partnerFacade.findPartner(partnerId));
    }

    @Operation(summary = "파트너 수정", description = "파트너 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @PutMapping
    public ResponseEntity<ApiResult<PartnerResponse.Update>> updatePartner(@RequestBody @Validated PartnerRequest.Update request){
        return ApiResult.createSuccess(partnerFacade.updatePartner(request));
    }

    @Operation(summary = "파트너 이메일 찾기", description = "파트너를 이메일을 찾는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너 이메일 찾기 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @PostMapping("/findEmail")
    public ResponseEntity<ApiResult<FindPartnerEmailResponse>> findPartnerEmail(@RequestBody @Validated FindPartnerEmailRequest request){
        return ApiResult.createSuccess(partnerFacade.findPartnerEmail(request));
    }

    @Operation(summary = "파트너 비밀번호 찾기", description = "파트너를 비밀번호를 찾는다다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파트너 비밀번호 찾기 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @PostMapping("/password")
    public ResponseEntity<ApiResult<FindPartnerPasswordResponse>> findPartnerPassword(@RequestBody @Validated FindPartnerPasswordRequest request){
        return ApiResult.createSuccess(partnerFacade.findPartnerPassword(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResult<FindPartnerProfileResponse>> findPartnerProfile(@JwtPartnerId Long partnerId){
        return ApiResult.createSuccess(partnerFacade.findPartnerProfile(partnerId));
    }
}
