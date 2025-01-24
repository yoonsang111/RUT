package com.stream.tour.domain.reservations.service;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.reservations.domain.Reservation;
import com.stream.tour.domain.reservations.dto.FindReservationRequest;
import com.stream.tour.domain.reservations.dto.FindReservationResponse;
import com.stream.tour.domain.reservations.dto.UpdateReservationDateRequest;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    Reservation findById(Long reservationId);
    List<Reservation> findByIdIn(List<Long> reservationIds);
    List<Reservation> findByOptionIdInAndReservationStartDateBetween(List<Long> optionIds, LocalDate startDate, Integer numberOfDaysToFetch);
    List<FindReservationResponse> findByIdIn(Long partnerId, FindReservationRequest request, Pageable pageable);

    Long updateReservationDate(Long reservationId, UpdateReservationDateRequest request);

    List<Reservation> findByOptionIdIn(List<Long> optionIds);

    boolean isMyReservation(List<ReservationEntity> reservationEntities, Partner partner);
}
