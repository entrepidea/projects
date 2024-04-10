package com.entrepidea.java.kafka;

/*
* This is a simple Kafka consumer. In order to run, start the kafka service on oracle9 and run a producer from anywhere
* in my case, simply run it from oracle9's ~/opt/kafka_2.13-3.7.0:
* kafka_2.13-3.7.0]$ bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
* any message the producer sends to the said topic "quickstart-events" will be picked up by the consumer.
*
* 04/01/24
*
* */
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "quickstart-events", groupId = "my-consumer-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}