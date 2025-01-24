package com.stream.tour.domain.reservations.infrastructure;

import com.stream.tour.domain.reservations.domain.Reservation;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.domain.reservations.service.port.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return reservationJpaRepository.findById(reservationId).map(ReservationEntity::toModel);
    }

    @Override
    public List<Reservation> findByIdIn(List<Long> reservationIds) {
        return reservationJpaRepository.findByIdIn(reservationIds).stream()
                .map(ReservationEntity::toModel)
                .toList();
    }

    @Override
    public List<Reservation> findByOptionIdInAndReservationStartDateBetween(List<Long> optionIds, LocalDate startDate, LocalDate reservationEndDate) {
        return reservationJpaRepository.findByOption_IdInAndReservationStartDateBetween(optionIds, startDate, reservationEndDate).stream()
                .map(ReservationEntity::toModel)
                .toList();
    }

    @Override
    public List<Reservation> findByOptionIdIn(List<Long> optionIds) {
        return reservationJpaRepository.findByOptionIdIn(optionIds).stream()
                .map(ReservationEntity::toModel)
                .toList();
    }

    @Override
    public List<Reservation> findByOptionId(Long optionId) {
        return reservationJpaRepository.findByOption_Id(optionId).stream()
                .map(ReservationEntity::toModel)
                .toList();
    }
}
