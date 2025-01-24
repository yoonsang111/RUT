package com.stream.tour.domain.customer.entity;

import com.stream.tour.domain.customer.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String email;
    private String phoneNumber;
    private String socialPlatform;
    private String socialId;

    private String fullName;
    private String lastName;
    private String firstName;
    private String passportNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar", length = 50)
    private Gender gender;
    private LocalDate dateOfBirth;
    private String etc;

    //==생성 메서드==//
    public static Customer createCustomer(String email, String phoneNumber, String socialPlatform, String socialId, String fullName, String lastName, String firstName, String passportNumber, Gender gender, LocalDate dateOfBirth, String etc) {
        return Customer.builder()
                .email(email)
                .phoneNumber(phoneNumber)
                .socialPlatform(socialPlatform)
                .socialId(socialId)
                .fullName(fullName)
                .lastName(lastName)
                .firstName(firstName)
                .passportNumber(passportNumber)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .etc(etc)
                .build();
    }
}
