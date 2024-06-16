package com.entrepidea.java.kafka.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "quickstart-events", groupId = "my-consumer-group")
    public void listen(String message) {

        LOGGER.info("Received message: {}", message);
    }
}