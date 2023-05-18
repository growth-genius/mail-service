package com.sgyj.mailservice.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Profile({"dev", "prod"})
@Component
@RequiredArgsConstructor
public class HtmlEmailService implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;


    private String setContext(String authCode) {
        Context context = new Context();
        context.setVariable("authCode", authCode);
        return templateEngine.process("mail", context);
    }

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(setContext(emailMessage.getMessage()), true);
            javaMailSender.send(mimeMessage);
            log.info("sent email: {}", emailMessage.getMessage());
        } catch (MessagingException e) {
            log.error("failed to send email", e);
        }
    }

    @Override
    public JavaMailSender getJavaMailSender() {
        return this.javaMailSender;
    }


}