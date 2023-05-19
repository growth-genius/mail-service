package com.sgyj.mailservice.modules.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgyj.mailservice.infra.mail.EmailMessage;
import com.sgyj.mailservice.infra.mail.EmailService;
import com.sgyj.mailservice.modules.dto.AccountDto;
import com.sgyj.mailservice.modules.enums.MailSubject;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailKafkaConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private String email;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Transactional
    @KafkaListener(topics = "password-mail-topic")
    public void processSendPasswordByMail(String kafkaMessage) throws JsonProcessingException {

        log.info("Kafka Message : ===> " + kafkaMessage);
        AccountDto accountDto = objectMapper.readValue(kafkaMessage, AccountDto.class);
        email = accountDto.getEmail();
        latch.countDown();
        emailService.sendEmail(createEmailByAuthCode(accountDto.getEmail()));

    }
    

    private EmailMessage createEmailByAuthCode(String email) {
        String authCode = RandomStringUtils.randomAlphanumeric(12);
        log.debug("AuthCode : " + authCode);
        return createEmailMessage(email, MailSubject.VALID_AUTHENTICATION_ACCOUNT, authCode);
    }

    private EmailMessage createEmailMessage(String email, MailSubject mailSubject, String authCode) {
        return EmailMessage.builder().to(email).subject(mailSubject.getSubject()).htmlCode(mailSubject.getHtmlCode()).message(authCode).build();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getEmail() {
        return email;
    }

}
