package com.stream.tour.domain.product.enums;

import lombok.Getter;

@Getter
public enum PickupType {
    ACCOMMODATION("숙소"),
    MEETING_PLACE("미팅 장소");

    private final String description;

    PickupType(String description) {
        this.description = description;
    }

    public static PickupType of(String pickupType) {
        return PickupType.valueOf(pickupType.toUpperCase());
    }
}
