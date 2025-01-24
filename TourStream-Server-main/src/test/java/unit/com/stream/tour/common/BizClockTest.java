package com.stream.tour.common;

import com.stream.tour.global.BizClock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BizClockTest {

    private BizClock bizClock = new BizClock();


    @DisplayName("14일 후 날짜를 반환한다.")
    @Test
    public void fourteenDaysFromTest() throws Exception {
        assertThat(bizClock.fourteenDaysFrom(LocalDate.of(2021, 9, 1)))
                .isEqualTo(LocalDate.of(2021, 9, 15));

        assertThat(bizClock.fourteenDaysFrom(LocalDate.of(2021, 9, 15)))
                .isEqualTo(LocalDate.of(2021, 9, 29));

        assertThat(bizClock.fourteenDaysFrom(LocalDate.of(2021, 9, 16)))
                .isEqualTo(LocalDate.of(2021, 9, 30));

        assertThat(bizClock.fourteenDaysFrom(LocalDate.of(2021, 9, 17)))
                .isEqualTo(LocalDate.of(2021, 10, 1));

        assertThat(bizClock.fourteenDaysFrom(LocalDate.of(2021, 9, 29)))
                .isEqualTo(LocalDate.of(2021, 10, 13));

        assertThat(bizClock.fourteenDaysFrom(LocalDate.of(2021, 9, 30)))
                .isEqualTo(LocalDate.of(2021, 10, 14));
    }

}