package com.stream.tour.domain.product.enums;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    RESERVED("예약완료"),
    REFUND_REQUESTED("환불요청"),
    PARTIAL_REFUND("부분환불"),
    FULL_REFUND("전체환불"),
    RESERVATION_WAITING("예약대기"),
    ;

    private String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public static ReservationStatus of(String reservationStatus) {
        for (ReservationStatus status : values()) {
            if (status.name().equals(reservationStatus)) {
                return status;
            }
        }
        return null;
    }
}
