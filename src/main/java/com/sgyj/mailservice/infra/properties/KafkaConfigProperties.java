package com.sgyj.mailservice.infra.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("kafka")
public class KafkaConfigProperties {

    private String bootstrapServersConfig;
    private String groupIdConfig;

}
