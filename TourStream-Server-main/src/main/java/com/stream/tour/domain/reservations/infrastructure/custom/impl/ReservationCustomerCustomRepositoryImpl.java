package com.stream.tour.domain.reservations.infrastructure.custom.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import com.stream.tour.domain.reservations.infrastructure.custom.ReservationCustomerCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.stream.tour.domain.reservations.entity.QReservationCustomerEntity.reservationCustomerEntity;


@RequiredArgsConstructor
@Repository
public class ReservationCustomerCustomRepositoryImpl implements ReservationCustomerCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReservationCustomerEntity> findByReservationIdInAndNotRepresentative(List<Long> reservationIds) {
        return queryFactory
                        .selectFrom(reservationCustomerEntity)
                        .where(inReservationIds(reservationIds), notRepresentative())
                        .fetch();
    }


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
        return !reservationIds.isEmpty() ? reservationCustomerEntity.reservationEntity.id.in(reservationIds) : null;
    }

}
