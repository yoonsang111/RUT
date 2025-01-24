package com.stream.tour.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Schema(name = "UpdateOptionRequest", description = "옵션 수정 요청 DTO")
public class UpdateOptionRequest {

    @Schema(name = "productId", description = "상품 id", required = true, example = "1")
    private Long productId;

    @Schema(name = "deleteOptionIds", description =  "삭제한 옵션 id", required = false, example = "[1,2]")
    private List<Long> deleteOptionIds;


    @Schema(name = "updateOptions", description =  "수정할 옵션 객체", required = true, example = "")
    private List<Update> updateOptions;

    @Getter
    @Setter
    @Schema(name = "UpdateOption", description = "옵션 수정 객체")
    public static class Update {
//        @Schema(name = "optionId", description = "옵션 ID", required = false, example = "1")
//        @Nullable
//        private Long optionId;

        @Schema(name = "subOptions", description = "하위 옵션", required = false, example = "1")
        @Nullable
        private List<Update> subOptions;

        @Schema(name="name", description = "옵션명", required = true, example = "오슬롭")
        private String name;

        @Schema(name = "stockQuantity", description = "재고 수량", required = true, example = "100")
        private int stockQuantity;

        @Schema(name="salesStatus", description = "판매 상태", required = true, example = "SALE / SOLD_OUT")
        private String salesStatus;

        @Schema(name = "siteCurrency", description = "사이트 판매가 통화", required = true, example = "KRW")
        private String siteCurrency;

        @Schema(name = "platformCurrency", description = "플랫폼 판매가 통화", required = true, example = "KRW")
        private String platformCurrency;

        @Schema(name = "sitePrice", description = "사이트 판매가", required = true, example = "10000")
        private BigDecimal sitePrice;

        @Schema(name = "platformPrice", description = "플랫폼 판매가", required = true, example = "10000")
        private BigDecimal platformPrice;

        @Schema(name = "salesStartDate", description = "판매 시작 일자", required = true, example = "2023-01-01")
        private LocalDate salesStartDate;

        @Schema(name = "salesEndDate", description = "판매 종료 일자", required = true, example = "2023-01-01")
        private LocalDate salesEndDate;

        @Schema(name = "applicationDay", description = "적용 요일", required = true, example = "TOTAL/ WEEKDAY/ WEEKEND/ HOLIDAY/ MONDAY/ TUESDAY/ WEDNESDAY/ THURSDAY/ FRIDAY/ SATURDAY/ SUNDAY")
        private String applicationDay;

        @Schema(name = "specificDate", description = "적용 특정 일자", required = true, example = "2023-01-01")
        private LocalDate specificDate;

        private Integer siteCurrencyId;
        private Integer platformCurrencyId;
    }
}
