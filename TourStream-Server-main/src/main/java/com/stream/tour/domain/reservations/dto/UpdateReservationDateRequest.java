package com.stream.tour.domain.reservations.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateReservationDateRequest {
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
}
