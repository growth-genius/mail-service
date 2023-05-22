package com.sgyj.mailservice.modules.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgyj.commonservice.dto.mail.EmailMessage;
import com.sgyj.mailservice.modules.common.annotation.BaseServiceAnnotation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public EmailMessage send(String kafkaTopic, EmailMessage emailMessage) throws JsonProcessingException {
        String accountJsonString = objectMapper.writeValueAsString(emailMessage);
        kafkaTemplate.send(kafkaTopic, accountJsonString);
        log.info("Kafka Producer send data from the Account MicroService : ");
        return emailMessage;
    }

}
