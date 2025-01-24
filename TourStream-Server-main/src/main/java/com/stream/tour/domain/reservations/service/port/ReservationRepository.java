package com.stream.tour.domain.reservations.service.port;

import com.stream.tour.domain.reservations.domain.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Optional<Reservation> findById(Long reservationId);
    List<Reservation> findByIdIn(List<Long> reservationIds);
    List<Reservation> findByOptionIdInAndReservationStartDateBetween(List<Long> optionIds, LocalDate startDate, LocalDate reservationEndDate);

    List<Reservation> findByOptionIdIn(List<Long> optionIds);

    List<Reservation> findByOptionId(Long optionId);
}
