package com.entrepidea.java.kafka.samples.sample2;

import java.util.HashMap;
import java.util.Map;

import com.entrepidea.java.kafka.samples.sample2.domain.Bar1;
import com.entrepidea.java.kafka.samples.sample2.domain.Bar2;
import com.entrepidea.java.kafka.samples.sample2.domain.Foo1;
import com.entrepidea.java.kafka.samples.sample2.domain.Foo2;
import org.apache.kafka.clients.admin.NewTopic;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper.TypePrecedence;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
/*
* code from https://github.com/spring-projects/spring-kafka/tree/main/samples/sample-02
*
* once one of the below two curl commands is run, the Controller (annotated with @RestController, acting as a Restful service)
* receives the message and resend it to one Kafka topic, depending on the nature of the message.
*   $ curl -X POST http://localhost:8080/send/foo/bar
*       this will go to the topic "foos", the Object type is Foo1 or Foo2
    $ curl -X POST http://localhost:8080/send/bar/baz
    *   this will go to the topic "bars", the Object type is Bar1 or Bar2
*
* Class MultiMethods is configured to be a Kafka listener - see the comments in that class to understand how message is being
* navigated and managed.
*
*
* 04/10/24, 04/13/24
* */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

}
