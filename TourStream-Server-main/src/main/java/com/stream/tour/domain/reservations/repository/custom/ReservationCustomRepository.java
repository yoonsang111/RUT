package com.stream.tour.domain.reservations.repository.custom;

import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;

import java.time.LocalDate;
import java.util.List;

public interface ReservationCustomRepository {

    List<ReservationEntity> findReservationStatus(List<Long> productIds, LocalDate startDate, Integer numberOfDaysToFetch);
    List<ReservationCustomerEntity> findCompanionsByReservationId(List<Long> reservationIds);
}
