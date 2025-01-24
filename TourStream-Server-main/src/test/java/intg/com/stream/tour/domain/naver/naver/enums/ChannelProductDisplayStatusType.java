package com.stream.tour.domain.naver.naver.enums;

import lombok.Getter;

@Getter
public enum ChannelProductDisplayStatusType {
    WAIT("전시 대기"), ON("전시 중"), SUSPENSION("전시 중지");

    private String description;

    ChannelProductDisplayStatusType(String description) {
        this.description = description;
    }
}
