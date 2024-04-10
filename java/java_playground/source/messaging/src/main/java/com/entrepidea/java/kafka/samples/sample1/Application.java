package com.entrepidea.java.kafka.samples.sample1;

import com.entrepidea.java.kafka.samples.sample1.domain.Foo;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

/**
 * Sample shows use of a dead letter topic.
 *
 * @author Gary Russell
 * @since 2.2.1
 *
 */

/*
* This program consists three parts: producer (via the Controller), consumers (those annotated with ApacheListener)
* and configuration (with bean annotations). Configuration of producer and consumer, such as selection of serializers is
* through the Factory beans as shown in this class (this part is updated - the original version uses yml config file).
*
* The workflow goes like:
*
* When this program is launched and up-running, it serves as a Restful service (the @RestController
* annotation makes it happen, and port 8080 is opened) and two Kafka consumers (via the listeners defined in this class).
*
* Once a restful client sends a POST request (curl -X POST http://localhost:8080/send/foo/bar),
* this request is taken in and re-sent to a topic (Topic1) via the Apache producer API "kafkaTemplate.send" in Controller class).
*
* The listeners in this class respond and print out the received unmarshalled messages.
*
*
*
* code from: https://github.com/spring-projects/spring-kafka/tree/main/samples/sample-01
*
* code updated and commented by Jonathan Yee
*
* 04/07/24
*
* */
@SpringBootApplication
public class Application {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    private final TaskExecutor exec = new SimpleAsyncTaskExecutor();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    /*
     * Boot will autowire this into the container factory.
     */
    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<String, Object> template) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }

    @Bean
    public RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }

    @KafkaListener(id = "fooGroup", topics = "topic1")
    public void listen(Foo foo) {
        logger.info("Received: " + foo);
        if (foo.getFoo().startsWith("fail")) {
            throw new RuntimeException("failed");
        }
        this.exec.execute(() -> System.out.println("Hit Enter to terminate..."));
    }

    @KafkaListener(id = "dltGroup", topics = "topic1.DLT")
    public void dltListen(byte[] in) {
        logger.info("Received from DLT: " + new String(in));
        this.exec.execute(() -> System.out.println("Hit Enter to terminate..."));
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("topic1", 1, (short) 1);
    }

    @Bean
    public NewTopic dlt() {
        return new NewTopic("topic1.DLT", 1, (short) 1);
    }

    @Bean
    @Profile("default") // Don't run from test(s)
    public ApplicationRunner runner() {
        return args -> {
            System.out.println("Hit Enter to terminate...");
            System.in.read();
        };
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Allow deserialization of any package

        return new DefaultKafkaConsumerFactory<>(props);
    }

    // Configure KafkaListenerContainerFactory
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}