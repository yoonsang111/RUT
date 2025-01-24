package com.stream.tour.domain.partner.service.port;

import com.stream.tour.global.email.EmailMessage;

public interface MailSender {
    void send(EmailMessage emailMessage);

}
