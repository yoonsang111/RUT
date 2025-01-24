package com.stream.tour.domain.reservations.infrastructure.entity;

import com.stream.tour.domain.bank.entity.BankCode;
import com.stream.tour.domain.customer.entity.Customer;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.product.enums.ReservationStatus;
import com.stream.tour.domain.reservations.domain.Reservation;
import com.stream.tour.domain.reservations.dto.UpdateReservationDateRequest;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "reservation")
@Entity
public class ReservationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "reservationEntity", cascade = CascadeType.ALL)
    private List<ReservationCustomerEntity> reservationCustomers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private OptionEntity option;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_code_id")
    private BankCode bankCode;

    @Comment("예약 번호")
    private String reservationNumber;

    @Comment("예약 사이트")
    private String reservationSite;

    private Integer quantity;

    @Comment("판매사")
    private String vendor;

    @Comment("인원수")
    private Integer attendance;

    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private LocalDate reservationFixedAt;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;
    private LocalDate purchasedAt;
    private BigDecimal paymentAmount;
    private String accountNumber;

    @Column(columnDefinition = "TINYINT", length = 1, nullable = false)
    private boolean isNewReservation;

    public Reservation toModel() {
        return Reservation.builder()
                .id(id)
                .reservationCustomers(reservationCustomers)
                .customer(customer)
                .option(option.toModel())
                .bankCode(bankCode)
                .reservationNumber(reservationNumber)
                .reservationSite(reservationSite)
                .quantity(quantity)
                .vendor(vendor)
                .attendance(attendance)
                .reservationStartDate(reservationStartDate)
                .reservationEndDate(reservationEndDate)
                .reservationFixedAt(reservationFixedAt)
                .reservationStatus(reservationStatus)
                .purchasedAt(purchasedAt)
                .paymentAmount(paymentAmount)
                .accountNumber(accountNumber)
                .isNewReservation(isNewReservation)
                .build();
    }

    //==생성 메서드==//
    public static ReservationEntity createReservation(Customer customer, OptionEntity option) {
        return ReservationEntity.builder()
                .option(option)
                .build();
    }

    //==비즈니스 메서드==//
    public boolean equalsId(ReservationEntity reservationEntity) {
        return this.id.equals(reservationEntity.getId());
    }

    public boolean equalsStartDate(ReservationEntity reservationEntity) {
        return this.reservationStartDate.equals(reservationEntity.getReservationStartDate());
    }

    public boolean isReserved() {
        return this.reservationStatus == ReservationStatus.RESERVED;
    }

    /**
     * 예약을 deep copy 한다.
     */
    public void deepCopyReservation(OptionEntity option) {
        this.id = null;
        this.option = option;
        this.reservationCustomers.forEach(reservationCustomer -> reservationCustomer.deepCopyReservationCustomer(this));
    }

    /**
     * 예약 일자를 변경한다.
     */
    public void updateReservationDate(UpdateReservationDateRequest request) {
        this.reservationStartDate = request.getReservationStartDate();
        this.reservationEndDate = request.getReservationEndDate();
    }
}
