package com.entrepidea.java.kafka.samples.sample3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@EnableKafka
public class KafkaBatchListenerConfig {

    private final ConsumerFactory<String, Object> consumerFactory;

    public KafkaBatchListenerConfig(ConsumerFactory<String, Object> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaBatchListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true); // Enable batch listening

        // Additional configurations for batch processing
        // e.g., factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }
}
