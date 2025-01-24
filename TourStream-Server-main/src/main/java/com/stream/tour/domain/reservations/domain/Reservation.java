package com.stream.tour.domain.reservations.domain;

import com.stream.tour.domain.bank.entity.BankCode;
import com.stream.tour.domain.customer.entity.Customer;
import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.product.enums.ReservationStatus;
import com.stream.tour.domain.reservations.dto.UpdateReservationDateRequest;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class Reservation {
    private Long id;
    @Builder.Default
    private List<ReservationCustomerEntity> reservationCustomers = new ArrayList<>();
    private Customer customer;
    private Option option;
    private BankCode bankCode;
    private String reservationNumber;
    private String reservationSite;
    private Integer quantity;
    private String vendor;
    private Integer attendance;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private LocalDate reservationFixedAt;
    private ReservationStatus reservationStatus;
    private LocalDate purchasedAt;
    private BigDecimal paymentAmount;
    private String accountNumber;
    private boolean isNewReservation;
    private boolean isReserved;


    public Reservation updateReservationDate(UpdateReservationDateRequest request) {
        return Reservation.builder()
                .id(id)
                .reservationCustomers(reservationCustomers)
                .customer(customer)
                .option(option)
                .bankCode(bankCode)
                .reservationNumber(reservationNumber)
                .reservationSite(reservationSite)
                .quantity(quantity)
                .vendor(vendor)
                .attendance(attendance)
                .reservationStartDate(request.getReservationStartDate())
                .reservationEndDate(request.getReservationEndDate())
                .reservationFixedAt(reservationFixedAt)
                .reservationStatus(reservationStatus)
                .purchasedAt(purchasedAt)
                .paymentAmount(paymentAmount)
                .accountNumber(accountNumber)
                .isNewReservation(isNewReservation)
                .build();
    }
}
