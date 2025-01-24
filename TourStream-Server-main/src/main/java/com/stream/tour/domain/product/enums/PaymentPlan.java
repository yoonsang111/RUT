package com.stream.tour.domain.product.enums;

import lombok.Getter;

@Getter
public enum PaymentPlan {
    ALL, FULL_PRE_PAYMENT, DEPOSIT_PAYMENT;

    public static PaymentPlan of(String paymentPlan) {
        return PaymentPlan.valueOf(paymentPlan.toUpperCase());
    }
}
