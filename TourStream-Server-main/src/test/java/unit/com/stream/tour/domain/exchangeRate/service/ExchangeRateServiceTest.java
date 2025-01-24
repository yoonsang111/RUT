package com.stream.tour.domain.exchangeRate.service;

import com.stream.tour.global.aop.aspect.RetryAspect;
import com.stream.tour.domain.exchangeRate.service.impl.ExchangeRateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Map;

@Slf4j
@Import(RetryAspect.class)
@SpringBootTest
class ExchangeRateServiceTest {

    @Autowired
    private ExchangeRateServiceImpl exchangeRateService;

    @DisplayName("open api 통신에 성공한다.")
    @Test
    void fetchCurrency() {

        String searchDate = "2023-09-30";
        List<Map<String, String>> list = exchangeRateService.fetchOpenApi(searchDate);

        Assertions.assertThat(list).isNotEmpty();
    }

    @DisplayName("@Retery 어노테이션 테스트")
    @Test
    void retryTest() {
        for (int i = 0; i < 5; i++) {
            log.info("client request i={}", i);
            exchangeRateService.saveExchangeRates();
        }
    }

}