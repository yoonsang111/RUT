package com.stream.tour.domain.option.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OptionResponse", description = "옵션 목록 응답")
public class OptionResponse {

    @Schema(name = "id", description = "옵션 ID", required = true, example = "1")
    private Long id;

    @JsonIgnore
    @Schema(name = "parentOptionId", description = "상위 옵션 ID", required = false, example = "1")
    private Long parentOptionId;


    @Schema(name = "name", description = "옵션명", required = true, example = "오슬롭")
    @NotNull
    private String name;

    @Schema(name="salesStatus", description = "판매 상태", required = true, example = "SALE / SOLD_OUT")
    @NotNull
    private String salesStatus;

    @Schema(name = "stockQuantity", description = "재고 수량", required = true, example = "100")
    @NotNull
    private Integer stockQuantity;

    @NotNull
    @Schema(name = "siteCurrency", description = "사이트 판매가 통화", required = true, example = "KRW")
    private String siteCurrency;

    @NotNull
    @Schema(name = "platformCurrency", description = "플랫폼 판매가 통화", required = true, example = "KRW")
    private String platformCurrency;

    @Schema(name = "sitePrice", description = "사이트 판매가", required = true, example = "10000")
    @NotNull
    private BigDecimal sitePrice;

    @Schema(name = "platformPrice", description = "플랫폼 판매가", required = true, example = "10000")
    @NotNull
    private BigDecimal platformPrice;

    @Schema(name = "salesStartDate", description = "판매 시작 일자", required = true, example = "2023-01-01")
    @NotNull
    private LocalDate salesStartDate;

    @Schema(name = "salesEndDate", description = "판매 마감 일자", required = true, example = "2023-12-31")
    @NotNull
    private LocalDate salesEndDate;

    @Schema(name = "applicationDay", description = "적용 요일", required = true, example = "TOTAL/ WEEKDAY/ WEEKEND/ HOLIDAY/ MONDAY/ TUESDAY/ WEDNESDAY/ THURSDAY/ FRIDAY/ SATURDAY/ SUNDAY")
    private String applicationDay;

    @Schema(name = "specificDate", description = "특정 일자", required = true, example = "2023-01-01")
    private LocalDate specificDate;

    @Schema(name = "subOptions", description = "하위 옵션", type = "array", implementation = OptionResponse.class)
    private List<OptionResponse> subOptions;

}
