package com.stream.tour.global;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Component
public class BizClock {
    public LocalDate now() {
        return LocalDate.now();
    }

    public LocalDate fourteenDaysFrom(LocalDate now) {
        return now.plusDays(14);
    }

    public static LocalDateTime atEndOfDay(LocalDate localDate) {
        return localDate.atTime(23, 59, 59);
    }
}
