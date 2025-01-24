package com.stream.tour.domain.product.enums;

import lombok.Getter;

@Getter
public enum RefundDetailDataType {
    RATE("정률제"),
    AMOUNT("정액제");

    private final String description;

    RefundDetailDataType(String description) {
        this.description = description;
    }

}
