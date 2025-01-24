package com.stream.tour.domain.exchangeRate.repository;

import com.stream.tour.domain.exchangeRate.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<ExchangeRate, Integer> {
    Optional<ExchangeRate> findByCurrencyCode(String code);
}
