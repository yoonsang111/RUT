package com.stream.tour.domain.product.dto;

import com.stream.tour.domain.product.enums.RefundPolicy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Schema(name = "refundDetails", description = "환불 정책 데이터")
@Getter
@Setter
public class SaveRefundDetailRequest {
    @Schema(name = "refundPolicy", description = "환불 정책", required = true, example = "RATE | AMOUNT")
    private RefundPolicy refundPolicy;

    @Schema(name = "value", description = "환불 정책 값", required = true, example = "0.1", maximum = "10_000_000_000", minimum = "0")
    @NotNull
    @Range(max = Integer.MAX_VALUE, min = 0)
    private BigDecimal value;

    @Schema(name = "startNumber", description = "시작", required = true, example = "1")
    @Range(max = Integer.MAX_VALUE, min = 1)
    @NotNull
    private Integer startNumber;

    @Schema(name = "endNumber", description = "종료", required = true, example = "3")
    @Range(max = Integer.MAX_VALUE, min = 1)
    @NotNull
    private Integer endNumber;
}
