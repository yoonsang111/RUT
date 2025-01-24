package com.stream.tour.domain.exchangeRate.service;

import com.stream.tour.domain.exchangeRate.entity.ExchangeRate;

import java.util.List;

public interface ExchangeRateService {
    public void fetchCurrency();
    public ExchangeRate findByCode(String code);

    public ExchangeRate findByCodeById(Integer id);

    public List<ExchangeRate> findAll();
}
