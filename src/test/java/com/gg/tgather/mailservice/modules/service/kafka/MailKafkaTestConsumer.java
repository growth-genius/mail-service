package com.gg.tgather.mailservice.modules.service.kafka;

import com.gg.tgather.commonservice.dto.mail.EmailMessage;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
class MailKafkaTestConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CountDownLatch latch;

    private EmailMessage emailMessage;

    @PostConstruct
    public void init() {
        this.latch = new CountDownLatch(1);
        this.emailMessage = new EmailMessage();
    }


    @KafkaListener(topics = "${kafka.user-topic.mail-send-topic}", errorHandler = "mailErrorHandler")
    @SendTo("${kafka.email-topic.send-email-fail-topic}")
    public void processSendPasswordByMail(String kafkaMessage) {
        log.error("Kafka Test Message : ===> {}", kafkaMessage);
        throw new KafkaException("kafka send mail Fail");
    }

    @KafkaListener(topics = "${kafka.email-topic.send-email-fail-topic}")
    public void sendEmailFail(String kafkaMessage) throws IOException {
        log.error("Test 메일 전송 실패 : {}", kafkaMessage);
        // 모르는 값은 변환하지 않는 설정
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        emailMessage = objectMapper.readValue(kafkaMessage, EmailMessage.class);
        latch.countDown();
        log.error("emailMessage :: {}", emailMessage);
    }

    public EmailMessage getEmailMessage() {
        return emailMessage;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
