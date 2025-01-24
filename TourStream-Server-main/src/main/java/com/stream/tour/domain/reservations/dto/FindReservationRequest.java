package com.stream.tour.domain.reservations.dto;

import com.stream.tour.domain.product.enums.ReservationStatus;
import com.stream.tour.global.validator.EnumValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FindReservationRequest {

    @Schema(description = "예약 시작일", example = "2021-01-01")
    private LocalDate paymentStartDate;

    @Schema(description = "예약 종료일", example = "2021-01-05")
    private LocalDate paymentEndDate;

    @Schema(description = "예약 번호", example = "123456789")
    private String reservationNumber;

    @Schema(description = "예약자 이름", example = "홍길동")
    private String customerName;

    @Schema(description = "예약자 휴대폰 번호", example = "01012345678")
    private String phoneNumber;

    private List<@EnumValidator(enumClass = ReservationStatus.class, required = false) String> reservationStatus;
}
