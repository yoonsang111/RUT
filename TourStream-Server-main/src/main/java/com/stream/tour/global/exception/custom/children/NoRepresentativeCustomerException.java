package com.stream.tour.global.exception.custom.children;

import com.stream.tour.domain.reservations.domain.Reservation;
import com.stream.tour.global.exception.custom.CustomException;

public class NoRepresentativeCustomerException extends CustomException {

    public NoRepresentativeCustomerException(Reservation reservation) {
        super(String.format("대표 고객이 존재하지 않습니다. reservationId = {%s}, reservationCustomer = {%s}", reservation.getId(), reservation.getReservationCustomers()));
    }
}
