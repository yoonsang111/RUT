package com.stream.tour.global.infrastructure;

import com.stream.tour.global.service.ClockHolder;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;

class SystemClockHolderTest {

    private final ClockHolder clockHolder = new SystemClockHolder();

    @Test
    void nowInLocalDate는_clock으로_넘어온_시간을_LocalDate타입으로_리턴한다() {
        Clock clock = Clock.fixed(Instant.parse("2023-03-24T00:00:00Z"), ZoneId.of("UTC"));
        LocalDate localDate = clockHolder.nowInLocalDate(clock);
        assertThat(localDate).isEqualTo(LocalDate.of(2023, 3, 24));
    }

    @Test
    void nowInLocalDateTime는_clock으로_넘어온_시간을_LocalDate타입으로_리턴한다() {
        Clock clock = Clock.fixed(Instant.parse("2023-03-24T08:12:10Z"), ZoneId.of("UTC"));
        LocalDateTime localDateTime = clockHolder.nowInLocalDateTime(clock);
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2023, 3, 24, 8, 12, 10));
    }

    @Test
    void addDays는_현재날짜에_일수를_더한_날짜를_리턴한다() {
        LocalDate currentDate = LocalDate.of(2023, 3, 24);
        LocalDate addedDate = clockHolder.addDays(currentDate, 14); // 14일 뒤
        assertThat(addedDate).isEqualTo(LocalDate.of(2023, 4, 7));
    }
}