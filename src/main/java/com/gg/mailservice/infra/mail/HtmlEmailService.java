package com.gg.mailservice.infra.mail;

import com.gg.commonservice.dto.mail.EmailMessage;
import com.gg.mailservice.modules.entity.Email;
import com.gg.mailservice.modules.repository.MailRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Profile({"dev", "prod"})
@Component
@RequiredArgsConstructor
public class HtmlEmailService implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final MailRepository mailRepository;

    @Override
    @Transactional
    public void sendEmail(EmailMessage emailMessage) {
        try {
            sendHttpEmail(emailMessage);

            Email email = Email.from(emailMessage);
            mailRepository.save(email);

            log.info("sent email: {}", emailMessage.getMessage());
        } catch (MessagingException e) {
            log.error("failed to send email", e);
        }
    }

    @Override
    public JavaMailSender getJavaMailSender() {
        return this.javaMailSender;
    }

    @Override
    public SpringTemplateEngine getSpringTemplateEngine() {
        return this.templateEngine;
    }


}