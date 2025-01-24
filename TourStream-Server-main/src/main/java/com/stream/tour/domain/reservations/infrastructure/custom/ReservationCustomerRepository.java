package com.stream.tour.domain.reservations.infrastructure.custom;

import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;

import java.util.List;

public interface ReservationCustomerRepository {
    List<ReservationCustomerEntity> findByReservationIdIn(List<Long> reservationIds);
}
