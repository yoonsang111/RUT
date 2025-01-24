package com.stream.tour.domain.reservations.dto;

import com.stream.tour.domain.product.enums.ReservationStatus;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
public class FindReservationResponse {

    private Long reservationId;                     // 예약 ID
    private String vendor;                          // 구매처
    private LocalDate purchasedAt;              // 결제일
    private String customerName;                    // 이름
    private String customerPhoneNumber;             // 연락처
    private String reservationNumber;               // 구매처 예약번호
    private LocalDate reservationDate;              // 예약일
    private String productName;                     // 상품명
    private String optionName;                      // 옵션명
    private LocalDate reservationFixedAt;           // 예약 확정일
    private int quantity;                           // 수량
    private BigDecimal paymentAmount;               // 결제금액
    private ReservationStatus reservationStatus;    // 예약 상태
    private String customerEmail;                   // 이메일
    private String pickupLocation;                  // 픽업장소
    private String arrivalLocation;                 // 도착장소
    private String messengerType;                   // 메신저 타입
    private String messengerId;                     // 메신저 ID

    private List<Companion> companions;

    public static List<Companion> toCompanions(List<ReservationCustomerEntity> reservationCustomers) {
        if (reservationCustomers == null || reservationCustomers.isEmpty()) {
            return null;
        }

        return reservationCustomers.stream()
                .map(reservationCustomer -> Companion.builder()
                        .customerName(reservationCustomer.getCustomer().getFullName())
                        .customerEmail(reservationCustomer.getCustomer().getEmail())
                        .customerPhoneNumber(reservationCustomer.getCustomer().getPhoneNumber())
                        .messengerType(reservationCustomer.getCustomer().getSocialPlatform())
                        .messengerId(reservationCustomer.getCustomer().getSocialId())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Companion {
        private String customerName;             // 이름
        private String customerPhoneNumber;      // 연락처
        private String customerEmail;            // 이메일
        private String messengerType;            // 메신저 타입
        private String messengerId;              // 메신저 ID
    }
}
