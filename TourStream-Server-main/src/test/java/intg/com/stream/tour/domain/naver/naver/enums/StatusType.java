package com.stream.tour.domain.naver.naver.enums;

import lombok.Getter;

/**
 * 네이버 API에 등록된 상품 상태
 */
@Getter
public enum StatusType {
    WAIT("판매 대기"),
    SALE("판매 중"),
    OUTOFSTOCK("품절"),
    UNADMISSION("승인 대기"),
    REJECTION("승인 거부"),
    SUSPENSION("판매 중지"),
    CLOSE("판매 종료"),
    PROHIBITION("판매 금지");

    private String description;

    StatusType(String description) {
        this.description = description;
    }
}
