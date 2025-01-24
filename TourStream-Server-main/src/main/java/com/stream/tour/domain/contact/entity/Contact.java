package com.stream.tour.domain.contact.entity;

import com.stream.tour.domain.contact.dto.ContactRequest;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Contact {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    private String companyName;

    private String representativeName;

    private String requesterName;

    private String phoneNumber;

    private String email;

    private String content;

    public static Contact fromDto(ContactRequest contactRequest) {
        return Contact.builder()
                .companyName(contactRequest.getCompanyName())
                .representativeName(contactRequest.getRepresentativeName())
                .requesterName(contactRequest.getRequesterName())
                .phoneNumber(contactRequest.getPhoneNumber())
                .email(contactRequest.getEmail())
                .content(contactRequest.getContent())
                .build();
    }
}
