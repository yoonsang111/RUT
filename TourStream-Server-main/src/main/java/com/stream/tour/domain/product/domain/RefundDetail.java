package com.stream.tour.domain.product.domain;

import com.stream.tour.domain.product.dto.SaveRefundDetailRequest;
import com.stream.tour.domain.product.enums.RefundPolicy;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public record RefundDetail (
        Long id,
        Product product,
        RefundPolicy refundPolicy,
        BigDecimal amount,
        BigDecimal rate,
        Integer startNumber,
        Integer endNumber
) {

    @Builder
    public RefundDetail {
    }

    public static List<RefundDetail> from(List<SaveRefundDetailRequest> refundDetails) {
        return refundDetails.stream()
                .map(RefundDetail::from)
                .toList();
    }

    public static RefundDetail from(SaveRefundDetailRequest refundDetail) {
        RefundPolicy requestedRefundPolicy = refundDetail.getRefundPolicy();
        validateParameters(requestedRefundPolicy, refundDetail.getValue());
        return new RefundDetail(
                null,
                null,
                requestedRefundPolicy,
                requestedRefundPolicy == RefundPolicy.AMOUNT ? refundDetail.getValue() : null,
                requestedRefundPolicy == RefundPolicy.RATE ? refundDetail.getValue() : null,
                refundDetail.getStartNumber(),
                refundDetail.getEndNumber()
        );
    }

    public RefundDetail deepCopyRefundDetail() {
        return RefundDetail.builder()
                .product(product)
                .refundPolicy(refundPolicy)
                .amount(amount)
                .rate(rate)
                .startNumber(startNumber)
                .endNumber(endNumber)
                .build();
    }


    public static void validateParameters(RefundPolicy refundPolicy, BigDecimal value) {
        switch (refundPolicy) {
            case AMOUNT -> {
                if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.valueOf(10_000_000_000L)) > 0) {
                    throw new IllegalArgumentException(String.format("정액제는 0 ~ 10_000_000_000 사이의 값만 입력할 수 있습니다. RefundPolicy: %s, amount: %s", refundPolicy, value));
                }
            }
            case RATE -> {
                if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.ONE) > 0) {
                    throw new IllegalArgumentException(String.format("정률제는 0 ~ 1 사이의 값만 입력할 수 있습니다. RefundPolicy: %s, rate: %s", refundPolicy, value));
                }
            }
        }
    }
}
