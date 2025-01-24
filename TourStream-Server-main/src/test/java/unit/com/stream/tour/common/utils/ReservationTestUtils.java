package com.stream.tour.common.utils;

import com.stream.tour.domain.bank.entity.BankCode;
import com.stream.tour.domain.customer.entity.Customer;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.product.enums.ReservationStatus;
import com.stream.tour.domain.reservations.dto.FindReservationStatusRequest;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReservationTestUtils {

    public static List<ReservationEntity> createReservations(List<ReservationCustomerEntity> reservationCustomers, Customer customer, OptionEntity option, BankCode bankCode) {
        List<ReservationEntity> reservationEntities = new ArrayList<>();
        Arrays.stream(ReservationEntity.class.getDeclaredConstructors()).forEach(constructor -> {
            if (constructor.getParameterCount() == 0) {
                constructor.setAccessible(true);
                try {
                    ReservationEntity reservationEntity = (ReservationEntity) constructor.newInstance();
                    setField(reservationCustomers, customer, option, bankCode, reservationEntity);
                    reservationEntities.add(reservationEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return reservationEntities;
    }

    public static ReservationEntity createReservation(List<ReservationCustomerEntity> reservationCustomers, Customer customer, OptionEntity option, BankCode bankCode) {
        return Arrays.stream(ReservationEntity.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    ReservationEntity reservationEntity = null;
                    try {
                        reservationEntity = (ReservationEntity) constructor.newInstance();
                        setField(reservationCustomers, customer, option, bankCode, reservationEntity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return reservationEntity;
                })
                .findAny().orElseThrow();
    }

    private static void setField(List<ReservationCustomerEntity> reservationCustomers, Customer customer, OptionEntity option, BankCode bankCode, ReservationEntity reservationEntity) {
        ReflectionTestUtils.setField(reservationEntity, "reservationCustomers", reservationCustomers);
        ReflectionTestUtils.setField(reservationEntity, "customer", customer);
        ReflectionTestUtils.setField(reservationEntity, "option", option);
        ReflectionTestUtils.setField(reservationEntity, "bankCode", bankCode);
        ReflectionTestUtils.setField(reservationEntity, "reservationNumber", "123-123123");
        ReflectionTestUtils.setField(reservationEntity, "reservationSite", "쿠팡");
        ReflectionTestUtils.setField(reservationEntity, "quantity", 123);
        ReflectionTestUtils.setField(reservationEntity, "vendor", "쿠팡");
        ReflectionTestUtils.setField(reservationEntity, "attendance", 11);
        ReflectionTestUtils.setField(reservationEntity, "reservationStartDate", LocalDate.of(2021, 1, 1));
        ReflectionTestUtils.setField(reservationEntity, "reservationEndDate", LocalDate.of(2021, 1, 5));
        ReflectionTestUtils.setField(reservationEntity, "reservationFixedAt", LocalDate.of(2021, 1, 1));
        ReflectionTestUtils.setField(reservationEntity, "reservationStatus", ReservationStatus.RESERVED);
        ReflectionTestUtils.setField(reservationEntity, "purchasedAt", LocalDate.of(2021, 1, 1));
        ReflectionTestUtils.setField(reservationEntity, "paymentAmount", BigDecimal.valueOf(10000));
        ReflectionTestUtils.setField(reservationEntity, "accountNumber", "123-123123-123");
        ReflectionTestUtils.setField(reservationEntity, "isNewReservation", true);
    }

    public static FindReservationStatusRequest createFindReservationStatusRequest(Long partnerId, Long productId) {
        FindReservationStatusRequest request = new FindReservationStatusRequest();
        request.setProductIds(List.of(productId));
        request.setStartDate(LocalDate.parse("2023-10-15"));
        return request;
    }
}
