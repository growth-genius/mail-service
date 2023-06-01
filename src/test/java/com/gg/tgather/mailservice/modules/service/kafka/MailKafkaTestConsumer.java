package com.gg.tgather.mailservice.modules.service.kafka;

import com.gg.tgather.commonservice.dto.mail.EmailMessage;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
class MailKafkaTestConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CountDownLatch latch = new CountDownLatch(1);

    private EmailMessage emailMessage;

    @KafkaListener(topics = "${kafka.user-topic.authentication-mail-topic}", errorHandler = "mailErrorHandler")
    @SendTo("${kafka.email-topic.send-email-fail-topic}")
    public void processSendPasswordByMail(String kafkaMessage) {
        log.info("Kafka Message : ===> {}", kafkaMessage);
        throw new KafkaException("kafka send mail Fail");
    }

    @KafkaListener(topics = "${kafka.email-topic.send-email-fail-topic}")
    public EmailMessage sendEmailFail(String kafkaMessage) throws IOException {
        log.error("메일 전송 실패 : {}", kafkaMessage);
        emailMessage = objectMapper.readValue(kafkaMessage, EmailMessage.class);
        latch.countDown();
        return emailMessage;
    }

    public EmailMessage getEmailMessage() {
        return emailMessage;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
