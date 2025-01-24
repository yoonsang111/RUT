package com.stream.tour.domain.reservations.infrastructure.custom;

import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;

import java.util.List;

public interface ReservationCustomerCustomRepository {
    List<ReservationCustomerEntity> findByReservationIdInAndNotRepresentative(List<Long> reservationIds);
}
