package com.stream.tour.domain.option.domain;

import com.stream.tour.domain.exchangeRate.entity.ExchangeRate;
import com.stream.tour.domain.option.enums.ApplicationDay;
import com.stream.tour.domain.option.enums.SalesStatus;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.reservations.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Option {
    private Long id;
    private Product product;
    private String name;
    private int stockQuantity;
    private SalesStatus salesStatus;
    private ExchangeRate siteCurrency;
    private ExchangeRate platformCurrency;
    private BigDecimal sitePrice;
    private BigDecimal platformPrice;
    private LocalDate salesStartDate;
    private LocalDate salesEndDate;
    private ApplicationDay applicationDay;
    private LocalDate specificDate;
    private Option parent;
    private List<Option> children;

    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();

    public Option deepCopyOption() {
        return Option.builder()
                .product(product)
                .name(name)
                .stockQuantity(stockQuantity)
                .salesStatus(salesStatus)
                .siteCurrency(siteCurrency)
                .platformCurrency(platformCurrency)
                .sitePrice(sitePrice)
                .platformPrice(platformPrice)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .applicationDay(applicationDay)
                .specificDate(specificDate)
                .parent(parent)
                .children(children)
                .reservations(reservations)
                .build();
    }
}

