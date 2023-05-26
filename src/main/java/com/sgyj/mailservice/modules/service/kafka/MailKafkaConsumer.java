package com.sgyj.mailservice.modules.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgyj.commonservice.annotation.BaseServiceAnnotation;
import com.sgyj.commonservice.dto.mail.EmailMessage;
import com.sgyj.mailservice.infra.mail.EmailService;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
public class MailKafkaConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    private final CountDownLatch latch = new CountDownLatch(1);

    @Transactional
    @KafkaListener(topics = "${kafka.user-topic.authentication-mail-topic}")
    public void processSendPasswordByMail(String kafkaMessage) throws JsonProcessingException {

        log.info("Kafka Message : ===> " + kafkaMessage);
        EmailMessage emailMessage = objectMapper.readValue(kafkaMessage, EmailMessage.class);
        latch.countDown();
        emailService.sendEmail(emailMessage);

    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
