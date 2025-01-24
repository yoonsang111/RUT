package com.stream.tour.common.utils;

import com.stream.tour.domain.exchangeRate.entity.ExchangeRate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

public class ExchangeRateTestUtils {

    public static ExchangeRate createExchangeRate() {
        return Arrays.stream(ExchangeRate.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    ExchangeRate exchangeRate = null;
                    try {
                        exchangeRate = (ExchangeRate) constructor.newInstance();
                        ReflectionTestUtils.setField(exchangeRate, "id", 1);
                        ReflectionTestUtils.setField(exchangeRate, "dealExchangeRate", "1.33");
                        ReflectionTestUtils.setField(exchangeRate, "currencyCode", "KRW");
                        ReflectionTestUtils.setField(exchangeRate, "currencyName", "원화");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return exchangeRate;
                })
                .findAny().orElseThrow();
    }
}
