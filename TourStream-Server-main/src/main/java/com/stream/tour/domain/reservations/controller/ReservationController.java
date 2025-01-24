package com.stream.tour.domain.reservations.controller;

import com.stream.tour.domain.reservations.dto.*;
import com.stream.tour.domain.reservations.facade.ReservationFacade;
import com.stream.tour.global.dto.ApiResult;
import com.stream.tour.global.jwt.annotation.JwtPartnerId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "예약 API", description = "예약 API")
@Validated
@RequestMapping("/reservations")
@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Operation(summary = "예약 현황 단건 조회", description = "예약 현황을 단건 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "예약 현황 단건 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @GetMapping("/status")
    public ApiResult<List<FindReservationStatusResponse>> findReservationStatus(
            @JwtPartnerId Long partnerId,
            @Validated @ParameterObject ReservationIdsDto reservationIdsDto
    ) {
        return ApiResult.createSuccessV2(reservationFacade.findReservationStatus(partnerId, reservationIdsDto.reservationIds()));
    }

    @Operation(summary = "예약 현황", description = "예약 현황을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "예약 현황 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @GetMapping("/status-list")
    public ApiResult<List<FindReservationStatusListResponse>> findReservationStatusList(
            @JwtPartnerId Long partnerId,
            @Validated @ParameterObject FindReservationStatusRequest request
    ) {
        return ApiResult.createSuccessV2(reservationFacade.findReservationStatusList(partnerId, request));
    }

    @Operation(summary = "예약 일자 변경", description = "예약 일자를 변경한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "예약 일자 변경 성공. 변경된 예약 아이디를 반환한다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @PutMapping("/status/{reservationId}/date")
    public ApiResult<Long> updateReservationDate(@JwtPartnerId @Schema(hidden = true) Long partnerId, @PathVariable Long reservationId, @RequestBody UpdateReservationDateRequest request) {
        return ApiResult.createSuccessV2(reservationFacade.updateReservationDate(partnerId, reservationId, request));
    }

    @Operation(summary = "예약 상세", description = "예약 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "예약 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @GetMapping
    public ApiResult<List<FindReservationResponse>> findReservations(
            @JwtPartnerId @Schema(hidden = true) Long partnerId,
            @ParameterObject @Validated FindReservationRequest request,
            @ParameterObject Pageable pageable
    ) {
        return ApiResult.createSuccessV2(reservationFacade.findReservations(partnerId, request, pageable));
    }

    @Operation(summary = "예약 상세 > 엑셀 업로드", description = "예약 상세 엑셀 파일을 업로드 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "엑셀 업로드 성공", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "엑셀 업로드 실패", content = @Content(schema = @Schema()))
    })
    @PostMapping("/{reservationId}/upload")
    public ResponseEntity<ApiResult<Void>> uploadExcelFile(@ParameterObject @PathVariable Long reservationId, MultipartFile excelFile) {
        reservationFacade.uploadExcelFile(reservationId, excelFile);
        return ApiResult.createSuccessWithNoContent();
    }
}
