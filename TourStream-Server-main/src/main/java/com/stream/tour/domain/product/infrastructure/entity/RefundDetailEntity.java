package com.stream.tour.domain.product.infrastructure.entity;

import com.stream.tour.domain.product.domain.RefundDetail;
import com.stream.tour.domain.product.dto.SaveRefundDetailRequest;
import com.stream.tour.domain.product.enums.RefundPolicy;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class RefundDetailEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Comment("데이터 타입")
    @Enumerated(STRING)
    private RefundPolicy refundPolicy;  // 1. rate(정률제) 2. amount(정액제)

    @Comment("금액")
    private BigDecimal amount;

    @Comment("비율")
    private BigDecimal rate;

    private Integer startNumber;

    private Integer endNumber;

    public RefundDetail toModel() {
        return RefundDetail.builder()
                .id(id)
                .product(product.toModel())
                .refundPolicy(refundPolicy)
                .amount(amount)
                .rate(rate)
                .startNumber(startNumber)
                .endNumber(endNumber)
                .build();
    }


    //==생성 메서드==//
    public static List<RefundDetailEntity> createRefundDetail(List<SaveRefundDetailRequest> requests) {
        return requests.stream()
                .map(RefundDetailEntity::createRefundDetail)
                .toList();
    }

    public static RefundDetailEntity createRefundDetail(SaveRefundDetailRequest request) {
        validateParameters(request.getRefundPolicy(), request.getValue());

        return RefundDetailEntity.builder()
                .refundPolicy(request.getRefundPolicy())
                .amount(request.getRefundPolicy() == RefundPolicy.AMOUNT ? request.getValue(): null)
                .rate(request.getRefundPolicy() == RefundPolicy.RATE ? request.getValue(): null)
                .startNumber(request.getStartNumber())
                .endNumber(request.getEndNumber())
                .build();
    }


    //==연관관계 메서드==//
    public void addProduct(ProductEntity product) {
        this.product = product;
    }

    public void deepCopyRefundDetail(ProductEntity product) {
        this.id = null;
        this.product = product;
    }

    //== 비즈니스 로직==//
    public static void validateParameters(RefundPolicy refundPolicy, BigDecimal value) {
        switch (refundPolicy) {
            case AMOUNT -> {
                if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.valueOf(10_000_000_000L)) > 0) {
                    throw new IllegalArgumentException(String.format("정액제는 0 ~ 10_000_000_000 사이의 값만 입력할 수 있습니다. RefundPolicy: %s, amount: %s", refundPolicy, value)); // TODO ApiErrorCode로 변경
                }
            }
            case RATE -> {
                if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.ONE) > 0) {
                    throw new IllegalArgumentException(String.format("정률제는 0 ~ 1 사이의 값만 입력할 수 있습니다. RefundPolicy: %s, rate: %s", refundPolicy, value)); // TODO ApiErrorCode로 변경
                }
            }
        }
    }
}

