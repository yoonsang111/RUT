package com.stream.tour.global.infrastructure;

import com.stream.tour.global.service.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public LocalDate nowInLocalDate() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime nowInLocalDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDate addDays(LocalDate currentDate, int daysToAdd) {
        return currentDate.plusDays(daysToAdd);
    }
}
