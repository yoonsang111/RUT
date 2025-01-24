package com.stream.tour.domain.naver.naver.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class NaverGetInquiryRequest {
    private int page;
    private int size;
    private String startSearchDate;
    private String endSearchDate;
    private String answered;

    public static MultiValueMap<String, String> toMultiValueMap(ObjectMapper objectMapper, int page, int size, LocalDate startSearchDate, LocalDate endSearchDate, String answered) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        NaverGetInquiryRequest request = NaverGetInquiryRequest.builder()
                .page(page)
                .size(size)
                .startSearchDate(startSearchDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .endSearchDate(endSearchDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .answered(answered)
                .build();

        multiValueMap.setAll(objectMapper.convertValue(request, new TypeReference<>() {}));
        return multiValueMap;
    }
}
