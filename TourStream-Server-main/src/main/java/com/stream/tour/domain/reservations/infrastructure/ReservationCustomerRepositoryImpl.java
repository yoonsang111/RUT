package com.stream.tour.domain.reservations.infrastructure;

import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import com.stream.tour.domain.reservations.infrastructure.custom.ReservationCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReservationCustomerRepositoryImpl implements ReservationCustomerRepository {

    private final ReservationCustomerJpaRepository reservationCustomerJpaRepository;

    @Override
    public List<ReservationCustomerEntity> findByReservationIdIn(List<Long> reservationIds) {
        return reservationCustomerJpaRepository.findByReservationIdInAndNotRepresentative(reservationIds);
    }
}
