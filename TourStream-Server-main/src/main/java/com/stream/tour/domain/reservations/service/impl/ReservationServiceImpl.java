package com.stream.tour.domain.reservations.service.impl;

import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.infrastructure.OptionJpaRepository;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.reservations.domain.Reservation;
import com.stream.tour.domain.reservations.dto.FindReservationRequest;
import com.stream.tour.domain.reservations.dto.FindReservationResponse;
import com.stream.tour.domain.reservations.dto.UpdateReservationDateRequest;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import com.stream.tour.domain.reservations.infrastructure.custom.ReservationCustomerRepository;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.domain.reservations.repository.query.ReservationQueryRepository;
import com.stream.tour.domain.reservations.service.ReservationService;
import com.stream.tour.domain.reservations.service.port.ReservationRepository;
import com.stream.tour.global.exception.custom.children.ResourceNotFoundException;
import com.stream.tour.global.service.ClockHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.stream.tour.global.utils.CollectorsUtil.getIdsFrom;
import static java.util.stream.Collectors.groupingBy;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationQueryRepository reservationQueryRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationCustomerRepository reservationCustomerRepository;

    private final ClockHolder clockHolder;
    private final Clock clock;
    private final OptionJpaRepository optionJpaRepository;

    @Override
    public Reservation findById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", reservationId));
    }

    @Override
    public List<Reservation> findByIdIn(List<Long> reservationIds) {
        return reservationRepository.findByIdIn(reservationIds);
    }

    @Override
    public List<Reservation> findByOptionIdInAndReservationStartDateBetween(List<Long> optionIds, LocalDate startDate, Integer numberOfDaysToFetch) {
        LocalDate reservationEndDate = clockHolder.addDays(LocalDate.now(clock), numberOfDaysToFetch);
        return reservationRepository.findByOptionIdInAndReservationStartDateBetween(optionIds, startDate, reservationEndDate);
    }

    @Override
    public List<FindReservationResponse> findByIdIn(Long partnerId, FindReservationRequest request, Pageable pageable) {
        List<FindReservationResponse> findReservationResponse = reservationQueryRepository
                .findReservations(partnerId, request, pageable)
                .getContent();

        Map<Long, List<ReservationCustomerEntity>> reservationCustomerMap =
                toMap(reservationCustomerRepository.findByReservationIdIn(getReservationIdsFrom(findReservationResponse)));

        findReservationResponse.forEach(response ->
                response.setCompanions(FindReservationResponse.toCompanions(reservationCustomerMap.get(response.getReservationId()))));

        return findReservationResponse;
    }

    @Override
    public Long updateReservationDate(Long reservationId, UpdateReservationDateRequest request) {
        Reservation reservation = this.findById(reservationId);
        reservation = reservation.updateReservationDate(request);
        return reservation.getId();
    }

    @Override
    public List<Reservation> findByOptionIdIn(List<Long> optionIds) {
        return reservationRepository.findByOptionIdIn(optionIds);
    }



    @Override
    public boolean isMyReservation(List<ReservationEntity> reservationEntities, Partner partner) {
        List<Long> optionIds = getIdsFrom(reservationEntities, reservation -> reservation.getOption().getId());
        List<OptionEntity> options = optionJpaRepository.findAllByIdIn(optionIds);
        getIdsFrom(options, option -> option.getProduct().getId());

        return false;

    }

    /**
     * 예약자 ID를 키로, 예약자의 동행자 목록을 값으로 하는 Map을 반환한다.
     */
    private Map<Long, List<ReservationCustomerEntity>> toMap(List<ReservationCustomerEntity> reservationCustomers) {
        return reservationCustomers.stream()
                .collect(groupingBy(reservationCustomer -> reservationCustomer.getReservationEntity().getId()));
    }

    /**
     * 예약자 ID를 반환한다.
     */
    private List<Long> getReservationIdsFrom(List<FindReservationResponse> contents) {
        return contents.stream()
                .map(FindReservationResponse::getReservationId)
                .toList();
    }
}
