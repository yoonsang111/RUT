package com.stream.tour.common.utils;

import com.stream.tour.domain.customer.entity.Customer;
import com.stream.tour.domain.customer.enums.Gender;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;

public class CustomerTestUtils {

    public static Customer createCustomer() {
        return Arrays.stream(Customer.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    Customer customer = null;
                    try {
                        customer = (Customer) constructor.newInstance();
                        ReflectionTestUtils.setField(customer, "email", "test@test.com");
                        ReflectionTestUtils.setField(customer, "phoneNumber", "01012345678");
                        ReflectionTestUtils.setField(customer, "socialPlatform", "KAKAO");
                        ReflectionTestUtils.setField(customer, "socialId", "test@test.com");
                        ReflectionTestUtils.setField(customer, "fullName", "홍길동");
                        ReflectionTestUtils.setField(customer, "lastName", "홍");
                        ReflectionTestUtils.setField(customer, "firstName", "길동");
                        ReflectionTestUtils.setField(customer, "passportNumber", "123456789");
                        ReflectionTestUtils.setField(customer, "gender", Gender.MALE);
                        ReflectionTestUtils.setField(customer, "dateOfBirth", LocalDate.of(1990, 1, 1));
                        ReflectionTestUtils.setField(customer, "etc", "etc");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return customer;
                })
                .findAny().orElseThrow();
    }
}
