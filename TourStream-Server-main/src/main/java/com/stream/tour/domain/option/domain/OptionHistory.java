package com.stream.tour.domain.option.domain;

import com.stream.tour.domain.option.enums.ApplicationDay;
import com.stream.tour.domain.option.enums.SalesStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class OptionHistory {
    private final Long id;
    private final Long optionId;

    private final Long productId;

    private final String name;

    private final int stockQuantity;

    private final SalesStatus salesStatus;

    private final int siteCurrencyId;

    private final int platformCurrencyId;

    private final BigDecimal sitePrice;

    private final BigDecimal platformPrice;

    private final LocalDate salesStartDate;

    private final LocalDate salesEndDate;

    private final ApplicationDay applicationDay;

    private final LocalDate specificDate;

    private final Long version;


    @Builder
    public OptionHistory(Long id, Long optionId, Long productId, String name, int stockQuantity, SalesStatus salesStatus, int siteCurrencyId, int platformCurrencyId, BigDecimal sitePrice, BigDecimal platformPrice, LocalDate salesStartDate, LocalDate salesEndDate, ApplicationDay applicationDay, LocalDate specificDate, Long version) {
        this.id = id;
        this.optionId = optionId;
        this.productId = productId;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.salesStatus = salesStatus;
        this.siteCurrencyId = siteCurrencyId;
        this.platformCurrencyId = platformCurrencyId;
        this.sitePrice = sitePrice;
        this.platformPrice = platformPrice;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.applicationDay = applicationDay;
        this.specificDate = specificDate;
        this.version = version;
    }

    public static OptionHistory createOptionHistory(Option option, long version) {
        return OptionHistory.builder()
                .optionId(option.getId())
                .productId(option.getProduct().getId())
                .name(option.getName())
                .stockQuantity(option.getStockQuantity())
                .salesStatus(option.getSalesStatus())
                .siteCurrencyId(option.getSiteCurrency().getId())
                .sitePrice(option.getSitePrice())
                .platformCurrencyId(option.getPlatformCurrency().getId())
                .platformPrice(option.getPlatformPrice())
                .salesStartDate(option.getSalesStartDate())
                .salesEndDate(option.getSalesEndDate())
                .applicationDay(option.getApplicationDay())
                .specificDate(option.getSpecificDate())
                .version(version)
                .build();
    }
}
