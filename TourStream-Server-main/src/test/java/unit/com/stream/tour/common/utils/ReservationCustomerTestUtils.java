package com.stream.tour.common.utils;

import com.stream.tour.domain.customer.entity.Customer;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

public class ReservationCustomerTestUtils {

    public static ReservationCustomerEntity createReservationCustomer(Customer customer) {
        return Arrays.stream(ReservationCustomerEntity.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    ReservationCustomerEntity reservationCustomer = null;
                    try {
                        reservationCustomer = (ReservationCustomerEntity) constructor.newInstance();
                        ReflectionTestUtils.setField(reservationCustomer, "customer", customer);
                        ReflectionTestUtils.setField(reservationCustomer, "isRepresentative", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return reservationCustomer;
                }).findAny().orElseThrow();
    }
}
