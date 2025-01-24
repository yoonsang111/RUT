package com.stream.tour.global.exception.custom.children;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.reservations.domain.Reservation;
import com.stream.tour.global.exception.custom.CustomException;

import java.util.List;

import static com.stream.tour.global.utils.CollectorsUtil.getIdsFrom;

public class NotMyReservationException extends CustomException {
    public NotMyReservationException(Partner partner, List<Reservation> reservations) {
        super("파트너가 소유한 예약이 아닙니다. 파트너 ID: " + partner.getId() + ", 예약 ID: " + getIdsFrom(reservations, Reservation::getId));
    }

    public NotMyReservationException(Partner partner, Reservation reservation) {
        super("파트너가 소유한 예약이 아닙니다. 파트너 ID: " + partner.getId() + ", 예약 ID: " + reservation.getId());
    }
}
