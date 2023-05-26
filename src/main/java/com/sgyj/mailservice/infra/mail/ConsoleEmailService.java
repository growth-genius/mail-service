package com.sgyj.mailservice.infra.mail;

import com.sgyj.commonservice.dto.mail.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Profile({"local", "test"})
@Component
public class ConsoleEmailService implements EmailService {

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        log.info("sent email: {}", emailMessage.getMessage());
    }

    @Override
    public JavaMailSender getJavaMailSender() {
        return null;
    }

    @Override
    public SpringTemplateEngine getSpringTemplateEngine() {
        return null;
    }

}
