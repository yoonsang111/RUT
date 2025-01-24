package com.stream.tour.global.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ClockHolder {
    LocalDate nowInLocalDate();
    LocalDateTime nowInLocalDateTime();
    LocalDate addDays(LocalDate currentDate, int daysToAdd);
}
