package com.stream.tour.common.mock;

import com.stream.tour.global.service.ClockHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final LocalDate localDate;
    private final LocalDateTime localDateTime;

    @Override
    public LocalDate nowInLocalDate() {
        return localDate;
    }

    @Override
    public LocalDateTime nowInLocalDateTime() {
        return localDateTime;
    }

    @Override
    public LocalDate addDays(LocalDate currentDate, int daysToAdd) {
        return currentDate.plusDays(daysToAdd);
    }
}
