package com.stream.tour;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class TourStreamServerApplicationTests {

    @Test
    void contextLoads() {
        new Random()
                .ints(1, 10)
                .limit(8)
                .forEach(System.out::print);
    }

}
