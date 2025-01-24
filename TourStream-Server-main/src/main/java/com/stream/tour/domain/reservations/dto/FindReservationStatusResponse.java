package com.stream.tour.domain.reservations.dto;

import com.stream.tour.domain.reservations.domain.Reservation;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import com.stream.tour.global.exception.custom.children.NoRepresentativeCustomerException;

public record FindReservationStatusResponse (
    Long reservationId,
    String reservationSite,
    String reservationNumber,
    String customerName,
    int numberOfPeople,
    String reservationStatus

) {

    public FindReservationStatusResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getReservationSite(),
                reservation.getReservationNumber(),
                reservation.getReservationCustomers().stream()
                                .filter(ReservationCustomerEntity::isRepresentative)
                                .findFirst()
                                .orElseThrow(() -> new NoRepresentativeCustomerException(reservation))
                                .getCustomer()
                                .getFullName(),
                reservation.getReservationCustomers().size(),
                reservation.getReservationStatus().name()
        );
    }
}
