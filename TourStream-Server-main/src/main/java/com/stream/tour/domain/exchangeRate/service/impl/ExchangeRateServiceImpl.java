package com.stream.tour.domain.exchangeRate.service.impl;

import com.stream.tour.domain.exchangeRate.entity.ExchangeRate;
import com.stream.tour.domain.exchangeRate.repository.ExchangeRepository;
import com.stream.tour.domain.exchangeRate.service.ExchangeRateService;
import com.stream.tour.global.aop.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Value("${open-api.key}")
    private String authKey;

    private final ExchangeRepository exchangeRepository;

    @Override
    public void fetchCurrency() {
        saveExchangeRates();
    }

    @Override
    public ExchangeRate findByCode(String code) {
        return exchangeRepository.findByCurrencyCode(code).orElseThrow();
    }

    @Override
    public ExchangeRate findByCodeById(Integer id) {
        return exchangeRepository.findById(id).orElse(null);
    }

    @Override
    public List<ExchangeRate> findAll() {
        return exchangeRepository.findAll();
    }

    /**
     * 해당 api는 오전 11시 이전에 오늘 환율을 요청하면 null 값을 반환하므로 11시 1분에 오늘 환율을 요청한다.
     */
    @Scheduled(cron = "0 1 11 * * *") // 매일 오전 11시 1분에 실행
    @Retry
    @Transactional
    public void saveExchangeRates() {
        log.info("open api 호출");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String searchDate = dateFormat.format(new Date());


        saveExchangeRate(fetchOpenApi(searchDate));

    }

    public List<Map<String, String>> fetchOpenApi(String searchDate) {
        RestTemplate rt = new RestTemplate();

        String apiUrl = UriComponentsBuilder
                .fromUriString("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON")
                .queryParam("authkey", authKey)
                .queryParam("data", "AP01")
                .queryParam("searchdate", searchDate)
                .toUriString();


        return rt.getForObject(apiUrl, List.class);
    }


    private void saveExchangeRate(List<Map<String, String>> rates) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        // 원화와 달러만 저장한다.
        rates.forEach(rate -> {
            if(rate.get("cur_unit").equals("KRW") || rate.get("cur_unit").equals("USD")) {
                exchangeRates.add(ExchangeRate.createExchangeRate(rate));
            }
        });

//        exchangeRepository.deleteAll();
        exchangeRepository.saveAll(exchangeRates);
    }
}
