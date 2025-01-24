package com.stream.tour.domain.reservations.repository.custom.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.domain.reservations.repository.custom.ReservationCustomRepository;
import com.stream.tour.global.BizClock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.stream.tour.domain.reservations.entity.QReservationCustomerEntity.reservationCustomerEntity;


@RequiredArgsConstructor
@Repository
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final BizClock bizClock;

    @Override
    public List<ReservationEntity> findReservationStatus(List<Long> productIds, LocalDate startDate, Integer numberOfDaysToFetch) {
        LocalDate endDate = getEndDate(startDate, numberOfDaysToFetch);

        return null;
//        return queryFactory
//                .selectFrom(reservation)
//                .where(inProductId(productIds), betweenReservationDate(startDate, endDate))
//                .fetch();
    }

    @Override
    public List<ReservationCustomerEntity> findCompanionsByReservationId(List<Long> reservationIds) {
        return queryFactory
                .selectFrom(reservationCustomerEntity)
                .where(inReservationIds(reservationIds), notRepresentative())
                .fetch();
    }

//    private BooleanExpression inProductId(List<Long> productIds) {
//        return !productIds.isEmpty() ? reservation.option.product.id.in(productIds) : null;
//    }

    /**
     * 대표자가 아닌 예약자를 반환한다.
     */
    private BooleanExpression notRepresentative() {
        return reservationCustomerEntity.isRepresentative.eq(false);
    }

    /**
     * 예약 ID가 일치하는 예약자를 반환한다.
     */
    private BooleanExpression inReservationIds(List<Long> reservationIds) {
        return null; // !reservationIds.isEmpty() ? reservationCustomer.reservation.id.in(reservationIds) : null;
    }

//    private BooleanExpression betweenReservationDate(LocalDate startDate, LocalDate endDate) {
//        return (startDate != null && endDate != null) ? reservation.reservationStartDate.between(startDate, endDate) : null;
//    }

    private LocalDate getEndDate(LocalDate startDate, Integer numberOfDaysToFetch) {
        return Optional.ofNullable(numberOfDaysToFetch)
                .map(startDate::plusDays)
                .orElse(bizClock.fourteenDaysFrom(startDate));

    }
}
