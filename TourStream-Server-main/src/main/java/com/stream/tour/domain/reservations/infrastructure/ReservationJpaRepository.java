package com.stream.tour.domain.reservations.infrastructure;

import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.domain.reservations.repository.custom.ReservationCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long>, ReservationCustomRepository {
    List<ReservationEntity> findByIdIn(List<Long> reservationIds);

    List<ReservationEntity> findByOptionIdIn(List<Long> optionIds);

    List<ReservationEntity> findByOption_IdInAndReservationStartDateBetween(List<Long> optionIds, LocalDate startDate, LocalDate reservationEndDate);

    List<ReservationEntity> findByOption_Id(Long optionId);
}
