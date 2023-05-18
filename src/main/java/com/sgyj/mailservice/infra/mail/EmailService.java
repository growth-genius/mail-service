package com.sgyj.mailservice.infra.mail;

import org.springframework.mail.javamail.JavaMailSender;


public interface EmailService {

    void sendEmail(EmailMessage emailMessage);


    JavaMailSender getJavaMailSender();

}
