package com.stream.tour.common.utils;

import com.stream.tour.domain.option.dto.OptionRequest;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.enums.ApplicationDay;
import com.stream.tour.domain.option.enums.SalesStatus;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OptionTestUtils {
    public static OptionEntity createOption(ProductEntity testProduct, List<ReservationEntity> reservationEntities) {
        return Arrays.stream(OptionEntity.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    OptionEntity option = null;
                    try {
                        option = (OptionEntity) constructor.newInstance();
                        ReflectionTestUtils.setField(option, "product", testProduct);
//                        ReflectionTestUtils.setField(option, "reservations", reservationEntities);
                        ReflectionTestUtils.setField(option, "name", "테스트 옵션");
                        ReflectionTestUtils.setField(option, "stockQuantity", 10);
                        ReflectionTestUtils.setField(option, "salesStatus", SalesStatus.SALE);
                        ReflectionTestUtils.setField(option, "siteCurrency", ExchangeRateTestUtils.createExchangeRate());
                        ReflectionTestUtils.setField(option, "platformCurrency", ExchangeRateTestUtils.createExchangeRate());
                        ReflectionTestUtils.setField(option, "sitePrice", BigDecimal.valueOf(10000));
                        ReflectionTestUtils.setField(option, "platformPrice", BigDecimal.valueOf(10000));
                        ReflectionTestUtils.setField(option, "salesStartDate", LocalDate.of(2020, 1, 1));
                        ReflectionTestUtils.setField(option, "salesEndDate", LocalDate.of(2020, 12, 31));
                        ReflectionTestUtils.setField(option, "applicationDay", ApplicationDay.HOLIDAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return option;
                }).findAny().orElseThrow();
    }

    public static Map<String, Object> createOptionRequestsMap() {
        return Map.of(
                "name", "테스트 옵션",
                "salesStatus", SalesStatus.SALE,
                "stockQuantity", 10,
                "siteCurrency", "KRW",
                "platformCurrency", "KRW",
                "sitePrice", "10000",
                "platformPrice", "10000",
                "salesStartDate", "2023-01-01",
                "salesEndDate", "2023-02-01",
                "applicationDay", ApplicationDay.HOLIDAY
        );
    }

    public static List<OptionRequest> createOptionRequests() {
        OptionRequest optionRequest = new OptionRequest();
        optionRequest.setName("테스트 옵션");
        optionRequest.setSalesStatus(SalesStatus.SALE.toString());
        optionRequest.setStockQuantity(10);
        optionRequest.setSiteCurrency("KRW");
        optionRequest.setPlatformCurrency("KRW");
        optionRequest.setSitePrice(BigDecimal.valueOf(10000));
        optionRequest.setPlatformPrice(BigDecimal.valueOf(100000));
        optionRequest.setSalesStartDate(LocalDate.parse("2023-01-01"));
        optionRequest.setSalesEndDate(LocalDate.parse("2023-02-01"));
        optionRequest.setApplicationDay(ApplicationDay.HOLIDAY.toString());

        return List.of(optionRequest);
    }

}
