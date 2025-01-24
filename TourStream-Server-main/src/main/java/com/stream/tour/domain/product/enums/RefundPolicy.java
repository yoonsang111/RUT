package com.stream.tour.domain.product.enums;

import lombok.Getter;


// 1. rate(정률제) 2. amount(정액제)
@Getter
public enum RefundPolicy {
    RATE, AMOUNT;

    public static RefundPolicy of(String refundPolicy) {
        return RefundPolicy.valueOf(refundPolicy.toUpperCase());
    }
}
