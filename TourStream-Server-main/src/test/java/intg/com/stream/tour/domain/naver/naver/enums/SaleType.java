package com.stream.tour.domain.naver.naver.enums;

import lombok.Getter;

@Getter
public enum SaleType {
    NEW("새 상품"), OLD("중고 상품");

    private String description;

    SaleType(String description) {
        this.description = description;
    }
}
