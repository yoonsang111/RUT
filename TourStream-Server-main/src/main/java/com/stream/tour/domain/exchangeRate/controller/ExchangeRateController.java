package com.stream.tour.domain.exchangeRate.controller;

import com.stream.tour.domain.exchangeRate.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
public class ExchangeRateController {
    private final ExchangeRateService currencyService;
}
