package com.stream.tour.domain.option.enums;

import lombok.Getter;

@Getter
public enum ApplicationDay {
    TOTAL, // 전체
    WEEKDAY, // 월~금
    WEEKEND, // 주말
    HOLIDAY, // 공휴일
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
}
