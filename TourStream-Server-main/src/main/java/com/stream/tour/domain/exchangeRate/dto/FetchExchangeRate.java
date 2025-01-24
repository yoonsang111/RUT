package com.stream.tour.domain.exchangeRate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchExchangeRate {
    private Integer result;

    @JsonProperty("cur_unit")
    private String curUnit;

    private String ttb;

    private String tts;

    @JsonProperty("deal_bas_r")
    private String dealBasR;

    private String bkpr;

    @JsonProperty("yy_efee_r")
    private String yyEfeeR;

    @JsonProperty("ten_dd_efee_r")
    private String tenDdEfeeR;

    @JsonProperty("kftc_bkpr")
    private String kftcBkpr;

    @JsonProperty("kftc_deal_bas_r")
    private String kftcDealBasR;

    @JsonProperty("cur_nm")
    private String curNm;

}

