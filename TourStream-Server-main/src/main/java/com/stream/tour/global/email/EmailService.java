package com.stream.tour.global.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(EmailMessage emailMessage){
        System.out.println("이메일 전송 완료");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessage.getTo());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getMessage());
        message.setFrom("todtt2210@gmail.com");
        message.setReplyTo("todtt2210@gmail.com");
        System.out.println("message : " + message);
        javaMailSender.send(message);
    }

    public EmailMessage createMailAndChangePassword(String memberEmail) {
        String str = getTempPassword();
        EmailMessage message = new EmailMessage();
        message.setTo(memberEmail);
        message.setSubject("TourStream 임시 비밀번호 발급");
        message.setMessage("안녕하세요. TourStream 임시비밀번호 안내 관련 메일입니다." + "회원님의 임시 비밀번호는 " + str + " 입니다."
        + " 로그인 후에 비밀번호를 변경해 주세요.");

        return message;
    }

    //랜덤함수로 임시비밀번호 구문 만들기
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    // thymeleaf를 통한 html 적용
    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }


//    public String sendMail(EmailMessage emailMessage, String type) {
//        String authNum = createCode();
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
//            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
//            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
//            mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
//            javaMailSender.send(mimeMessage);
//
//            log.info("Success");
//
//            return authNum;
//
//        } catch (MessagingException e) {
//            log.info("fail");
//            throw new RuntimeException(e);
//        }
//    }
}
