package com.gg.tgather.mailservice.modules.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg.tgather.commonservice.annotation.BaseServiceAnnotation;
import com.gg.tgather.commonservice.dto.mail.EmailMessage;
import com.gg.tgather.mailservice.infra.mail.EmailService;
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
    private final KafkaProducer kafkaProducer;

    private String email;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Transactional
    @KafkaListener(topics = "${kafka.user-topic.authentication-mail-topic}")
    public void processSendPasswordByMail(String kafkaMessage) {
        EmailMessage emailMessage = EmailMessage.builder().build();
        try {
            log.info("Kafka Message : ===> " + kafkaMessage);
            emailMessage = objectMapper.readValue(kafkaMessage, EmailMessage.class);
            latch.countDown();
            emailService.sendEmail(emailMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
            kafkaProducer.sendFailedEmail(emailMessage);
        }
    }

    @Transactional
    @KafkaListener(topics = "${kafka.travel-group-topic.send-request-join-travel-group-topic}")
    public void processJoinTravelGroupByMail(String kafkaMessage) {
        EmailMessage emailMessage = EmailMessage.builder().build();
        try {
            log.info("Kafka Message : ===> " + kafkaMessage);
            emailMessage = objectMapper.readValue(kafkaMessage, EmailMessage.class);
            latch.countDown();
            emailService.sendEmail(emailMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
            kafkaProducer.sendFailedEmail(emailMessage);
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }


}
