package com.stream.tour.domain.exchangeRate.entity;

import com.stream.tour.global.audit.CreatedInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeRate extends CreatedInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_rate_id")
    private Integer id;

    @Comment("매매 기준율 환율")
    @Column(nullable = false)
    private String dealExchangeRate;

    @Comment("통화 코드")
    @Column(nullable = false)
    private String currencyCode;

    @Comment("통화명")
    @Column(nullable = false)
    private String currencyName;

    //==생성 메서드==//
    public static ExchangeRate createExchangeRate(Map<String, String> rate) {

        String dealExchangeRate = rate.get("deal_bas_r").replaceAll(",","");
        String currencyCode = rate.get("cur_unit").substring(0,3);
        String currencyName = rate.get("cur_nm");

        return ExchangeRate.builder()
                .dealExchangeRate(dealExchangeRate)
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .build();
    }
}
