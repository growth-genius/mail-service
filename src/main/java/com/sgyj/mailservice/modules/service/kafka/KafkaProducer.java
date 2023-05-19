package com.sgyj.mailservice.modules.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgyj.mailservice.modules.common.annotation.BaseServiceAnnotation;
import com.sgyj.mailservice.modules.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@BaseServiceAnnotation
@RequiredArgsConstructor
class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public AccountDto send(String kafkaTopic, AccountDto accountDto) throws JsonProcessingException {
        String accountJsonString = objectMapper.writeValueAsString(accountDto);
        kafkaTemplate.send(kafkaTopic, accountJsonString);
        log.info("Kafka Producer send data from the Account MicroService : ");
        return accountDto;
    }

}
