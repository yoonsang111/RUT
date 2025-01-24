package com.stream.tour.domain.reservations.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stream.tour.domain.customer.entity.QCustomer;
import com.stream.tour.domain.option.entity.QOptionEntity;
import com.stream.tour.domain.product.enums.ReservationStatus;
import com.stream.tour.domain.product.infrastructure.entity.QProductEntity;
import com.stream.tour.domain.reservations.dto.FindReservationRequest;
import com.stream.tour.domain.reservations.dto.FindReservationResponse;
import com.stream.tour.domain.reservations.infrastructure.entity.QReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;



@RequiredArgsConstructor
@Repository
public class ReservationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<FindReservationResponse> findReservations(Long partnerId, FindReservationRequest request, Pageable pageable) {
        QProductEntity product = QProductEntity.productEntity;
        QOptionEntity option = QOptionEntity.optionEntity;
        QReservationEntity reservation = QReservationEntity.reservationEntity;
        QCustomer customer = QCustomer.customer;

        List<FindReservationResponse> findReservationResponses = queryFactory
                .select(Projections.fields(
                        FindReservationResponse.class,
                        reservation.id.as("reservationId"),
                        reservation.vendor,
                        reservation.purchasedAt,
                        customer.fullName.as("customerName"),
                        customer.phoneNumber.as("customerPhoneNumber"),
                        reservation.reservationNumber,
                        reservation.reservationStartDate.as("reservationDate"),
                        product.name.as("productName"),
                        option.name.as("optionName"),
                        reservation.reservationFixedAt,
                        reservation.quantity,
                        reservation.paymentAmount,
                        reservation.reservationStatus,
                        customer.email.as("customerEmail"),
                        product.pickupLocation,
                        customer.socialPlatform.as("messengerType"),
                        customer.socialId.as("messengerId")
                ))
                .from(product)
                    .join(product.options, option)
                    .join(option.reservationEntities, reservation)
                    .join(reservation.customer, customer)
                .where(
                        eqPartnerId(partnerId),
                        betweenPaymentDate(
                                request.getPaymentStartDate(),
                                request.getPaymentEndDate()
                        ),
                        containsReservationNumber(request.getReservationNumber()),
                        containsCustomerName(request.getCustomerName()),
                        containsCustomerPhoneNumber(request.getPhoneNumber()),
                        inReservationStatus(request.getReservationStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long totalElements = queryFactory
                .select(reservation.count())
                .from(product)
                    .join(product.options, option)
                    .join(option.reservationEntities, reservation)
                    .join(reservation.customer, customer)
                .where(
                        eqPartnerId(partnerId),
                        betweenPaymentDate(
                                request.getPaymentStartDate(),
                                request.getPaymentEndDate()
                        ),
                        containsReservationNumber(request.getReservationNumber()),
                        containsCustomerName(request.getCustomerName()),
                        containsCustomerPhoneNumber(request.getPhoneNumber()),
                        inReservationStatus(request.getReservationStatus())
                )
                .fetchOne();

        return new PageImpl<>(findReservationResponses, pageable, totalElements);
    }


    // ####################################
    // # 여기서부터는 검색 조건을 위한 메서드들입니다.
    // ####################################
    /**
     * 예약 상태 검색
     */
    private BooleanExpression inReservationStatus(List<String> reservationStatusList) {
        if (reservationStatusList == null || reservationStatusList.isEmpty()) {
            return null;
        }

        QReservationEntity reservation = QReservationEntity.reservationEntity;

        return reservationStatusList.stream()
                .map(reservationStatus -> reservation.reservationStatus.eq(ReservationStatus.of(reservationStatus)))
                .reduce(BooleanExpression::or)
                .orElse(null);

    }

    /**
     * 고객 전화번호 포함 검색
     */
    private BooleanExpression containsCustomerPhoneNumber(String phoneNumber) {
        QReservationEntity reservation = QReservationEntity.reservationEntity;
        return phoneNumber != null ? reservation.customer.phoneNumber.contains(phoneNumber) : null;
    }

    /**
     * 고객명 포함 검색
     */
    private BooleanExpression containsCustomerName(String customerName) {
        QReservationEntity reservation = QReservationEntity.reservationEntity;
        return customerName != null ? reservation.customer.fullName.contains(customerName) : null;
    }

    /**
     * 예약번호 포함 검색
     */
    private BooleanExpression containsReservationNumber(String reservationNumber) {
        QReservationEntity reservation = QReservationEntity.reservationEntity;
        return reservationNumber != null ? reservation.reservationNumber.contains(reservationNumber) : null;
    }

    /**
     * 결제일자 범위 검색
     */
    private BooleanExpression betweenPaymentDate(LocalDate paymentStartDate, LocalDate paymentEndDate) {
        QReservationEntity reservation = QReservationEntity.reservationEntity;
        if (paymentStartDate == null && paymentEndDate == null) {
            return null;
        }

        if (paymentStartDate == null) {
            return reservation.purchasedAt.before(paymentEndDate);
        }

        if (paymentEndDate == null) {
            return reservation.purchasedAt.after(paymentStartDate);
        }

        return reservation.purchasedAt.between(paymentStartDate, paymentEndDate);
    }

    /**
     * 파트너 ID 검색
     */
    private BooleanExpression eqPartnerId(Long partnerId) {
        QProductEntity product = QProductEntity.productEntity;
        return partnerId != null ? product.partner.id.eq(partnerId) : null;
    }
}
