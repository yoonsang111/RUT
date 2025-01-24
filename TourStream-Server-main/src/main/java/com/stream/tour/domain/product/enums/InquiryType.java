package com.stream.tour.domain.product.enums;

import lombok.Getter;

@Getter
public enum InquiryType {
    ALL, DIRECT, INSTANT_PAYMENT;

    public static InquiryType of(String inquiryType) {
        return InquiryType.valueOf(inquiryType.toUpperCase());
    }
}
