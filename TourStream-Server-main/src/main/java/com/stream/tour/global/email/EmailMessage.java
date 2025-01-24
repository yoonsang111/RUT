package com.stream.tour.global.email;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    private String to;

    private String subject;

    private String message;
}
