package com.fourback.runus.global.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * packageName    : com.fourback.runus.global.mail
 * fileName       : MailConfig
 * author         : 김은정
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        김은정            최초 생성/yml에 작성했지만 반영이 안되서 추가
 */
@Configuration
@RequiredArgsConstructor
public class MailConfig {

    @Value("${spring.mail.username}")
    private String MAIL;

    @Value("${spring.mail.password}")
    private String PASSWORD;


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(MAIL);
        mailSender.setPassword(PASSWORD);

        java.util.Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
