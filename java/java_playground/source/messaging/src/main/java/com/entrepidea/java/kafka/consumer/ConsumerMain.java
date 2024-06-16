package com.entrepidea.java.kafka.consumer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This is a simple Kafka consumer. In order to run, start the kafka service on oracle9 and run a producer from anywhere
 * in my case, simply run it from oracle9's ~/opt/kafka_2.13-3.7.0:
 * kafka_2.13-3.7.0]$ bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
 * any message the producer sends to the said topic "quickstart-events" will be picked up by the consumer.
 *
 * 04/01/24
 *
 * */
@SpringBootApplication
public class ConsumerMain implements ApplicationRunner
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConsumerMain.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
