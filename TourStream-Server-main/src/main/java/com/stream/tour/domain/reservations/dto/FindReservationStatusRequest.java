package com.stream.tour.domain.reservations.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FindReservationStatusRequest {

    @NotNull @Size(min =1, max = 100)
    @Schema(description = "상품 ID", example = "[1, 2, 3]", type = "array")
    private List<Long> productIds;

    @NotNull
    @Schema(description = "시작일", example = "2023-10-15", required = true)
    private LocalDate startDate;

    @NotNull
    @Positive
    @Schema(description = "조회할 날짜의 수(default: 14)", example = "1")
    private Integer numberOfDaysToFetch;
}
