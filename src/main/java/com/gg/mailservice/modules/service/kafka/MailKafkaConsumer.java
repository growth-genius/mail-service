package com.gg.mailservice.modules.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg.commonservice.annotation.BaseServiceAnnotation;
import com.gg.commonservice.dto.mail.EmailMessage;
import com.gg.commonservice.dto.mail.MailSubject;
import com.gg.mailservice.infra.mail.EmailService;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class MailKafkaConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    private String email;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Transactional
    @KafkaListener(topics = "${kafka.user-topic.authentication-mail-topic}")
    public void processSendPasswordByMail(String kafkaMessage) throws JsonProcessingException {

        log.info("Kafka Message : ===> " + kafkaMessage);
        EmailMessage emailMessage = objectMapper.readValue(kafkaMessage, EmailMessage.class);
        latch.countDown();
        emailService.sendEmail(emailMessage);

    }
    
    private EmailMessage createEmailByAuthCode(String email) {
        String authCode = RandomStringUtils.randomAlphanumeric(12);
        log.debug("AuthCode : " + authCode);
        return createEmailMessage(email, MailSubject.VALID_AUTHENTICATION_ACCOUNT, authCode);
    }

    private EmailMessage createEmailMessage(String email, MailSubject mailSubject, String authCode) {
        return EmailMessage.builder().to(email).mailSubject(mailSubject).message(authCode).build();
    }
    public CountDownLatch getLatch() {
        return latch;
    }


}
