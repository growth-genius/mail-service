package com.gg.tgather.mailservice.modules.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg.tgather.commonservice.dto.mail.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailKafkaTestProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(String kafkaTopic, EmailMessage emailMessage) throws JsonProcessingException {
        String accountJsonString = objectMapper.writeValueAsString(emailMessage);
        kafkaTemplate.send(kafkaTopic, accountJsonString);
        log.info("Kafka Producer send data from the Account MicroService : ");
    }

}
