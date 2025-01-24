package com.stream.tour.domain.reservations.entity;

import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.global.audit.BaseEntity;
import com.stream.tour.domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class ReservationCustomerEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_customer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservationEntity;

    @Column(columnDefinition = "TINYINT", length = 1)
    private boolean isRepresentative;

    public void deepCopyReservationCustomer(ReservationEntity reservationEntity) {
        this.id = null;
        this.reservationEntity = reservationEntity;
    }
}
