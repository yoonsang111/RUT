package com.stream.tour.domain.partner.infrastructure;

import com.stream.tour.domain.partner.service.port.MailSender;
import com.stream.tour.global.email.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void send(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessage.getTo());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getMessage());
        message.setFrom("todtt2210@gmail.com");
        message.setReplyTo("todtt2210@gmail.com");
        javaMailSender.send(message);


        log.info("================ 이메일 전송 완료 ==================");
        log.info("이메일 전송 메세지 : {}", message);
    }
}
