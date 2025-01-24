package com.stream.tour.domain.product.enums;

import lombok.Getter;

@Getter
public enum Transportation {
    CAR, WALK, PICKUP, MEETING_PLACE;

    public static Transportation of(String transportation) {
        return Transportation.valueOf(transportation.toUpperCase());
    }
}
