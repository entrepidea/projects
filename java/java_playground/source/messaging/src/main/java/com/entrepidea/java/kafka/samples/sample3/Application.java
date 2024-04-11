package com.entrepidea.java.kafka.samples.sample3;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.entrepidea.java.kafka.samples.sample3.domain.Foo2;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

    final static CountDownLatch LATCH = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        LATCH.await();
        Thread.sleep(5_000);
        context.close();
    }

    @Bean
    public RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }

    @Bean
    public BatchMessagingMessageConverter batchConverter() {
        return new BatchMessagingMessageConverter(converter());
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("topic2").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("topic3").partitions(1).replicas(1).build();
    }


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> prodProps = new HashMap<>();
        prodProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        prodProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prodProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        DefaultKafkaProducerFactory<String, Object> factory = new DefaultKafkaProducerFactory<>(prodProps);
        factory.setTransactionIdPrefix("tx");
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Allow deserialization of any package
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaBatchListenerConfig kafkaBatchListenerConfig(ConsumerFactory<String, Object> consumerFactory) {
        return new KafkaBatchListenerConfig(consumerFactory);
    }
}

