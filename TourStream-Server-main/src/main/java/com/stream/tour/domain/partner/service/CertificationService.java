package com.stream.tour.domain.partner.service;

import com.stream.tour.domain.partner.service.port.MailSender;
import com.stream.tour.global.email.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final MailSender mailSender;

    public void send(EmailMessage emailMessage) {
        mailSender.send(emailMessage);
    }
}
