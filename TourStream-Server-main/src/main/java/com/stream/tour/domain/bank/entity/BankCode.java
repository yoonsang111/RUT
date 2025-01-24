package com.stream.tour.domain.bank.entity;

import com.stream.tour.domain.bank.enums.DataType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Builder(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class BankCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_code_id")
    private Long id;

    private DataType dataType;
    private String bankCode;
    private String vendor;
}
