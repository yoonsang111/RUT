package com.stream.tour.domain.reservations.infrastructure;

import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import com.stream.tour.domain.reservations.infrastructure.custom.ReservationCustomerCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationCustomerJpaRepository extends JpaRepository<ReservationCustomerEntity, Long>, ReservationCustomerCustomRepository {
}
