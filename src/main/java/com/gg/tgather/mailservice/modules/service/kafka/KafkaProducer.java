package com.gg.tgather.mailservice.modules.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg.tgather.commonservice.annotation.BaseServiceAnnotation;
import com.gg.tgather.commonservice.dto.mail.EmailMessage;
import com.gg.tgather.commonservice.properties.KafkaEmailTopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaEmailTopicProperties kafkaEmailTopicProperties;

    public EmailMessage send(String kafkaTopic, EmailMessage emailMessage) throws JsonProcessingException {
        String accountJsonString = objectMapper.writeValueAsString(emailMessage);
        kafkaTemplate.send(kafkaTopic, accountJsonString);
        log.info("Kafka Producer send data from the Account MicroService : ");
        return emailMessage;
    }

    @Transactional
    @KafkaListener(topics = "${kafka.email-topic.fail.send-authentication-mail-fail-topic}")
    public void sendFailedEmail(EmailMessage emailMessage) {
        kafkaTemplate.send(kafkaEmailTopicProperties.getSendEmailFailTopic(), emailMessage.getAccountId());
        log.info("Kafka Producer send data from the Account MicroService");
    }

}
