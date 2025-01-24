package com.stream.tour.domain.reservations.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ReservationIdsDto(

        @NotNull
        @Size(min = 1, max = 1000)
        List<Long> reservationIds
) {
}
