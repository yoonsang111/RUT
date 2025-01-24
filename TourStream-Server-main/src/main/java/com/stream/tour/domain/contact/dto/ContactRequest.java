package com.stream.tour.domain.contact.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ContactRequest {

    @Length(max = 50)
    @NotNull
    private String companyName;

    @Length(max = 20)
    @NotNull
    private String representativeName;

    @Length(max = 20)
    @NotNull
    private String requesterName;

    @Length(max = 20)
    @NotNull
    private String phoneNumber;

    @Length(max = 50)
    @NotNull
    private String email;

    @Length(max = 500)
    @NotNull
    private String content;
}
