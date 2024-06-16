package com.entrepidea.java.kafka.samples.sample3;

import com.entrepidea.java.kafka.samples.sample3.domain.Foo1;
import com.entrepidea.java.kafka.samples.sample3.domain.Foo2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component 
class Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(id = "fooGroup2", topics = "topic2", containerFactory = "kafkaBatchListenerContainerFactory")
    public void listen1(List<byte[]> foos) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        LOGGER.info("Received: {}, first object: {}",foos, objectMapper.readValue(foos.getFirst(), Foo2.class).getFoo().toUpperCase());
        LOGGER.info("Received: {}, last object: {}",foos, objectMapper.readValue(foos.getLast(), Foo2.class).getFoo().toUpperCase());

        foos.forEach(f -> {
            try {
                kafkaTemplate.send("topic3", objectMapper.readValue(f, Foo1.class).getFoo().toUpperCase());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        LOGGER.info("Messages sent, hit Enter to commit tx");
        System.in.read();
    }

    @KafkaListener(id = "fooGroup3", topics = "topic3", containerFactory = "kafkaBatchListenerContainerFactory")
    public void listen2(List<String> in) {
        LOGGER.info("Received: " + in);
        Application.LATCH.countDown();
    }

}
