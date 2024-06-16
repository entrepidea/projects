package com.entrepidea.java.kafka.samples.sample2;

import com.entrepidea.java.kafka.samples.sample2.domain.Bar1;
import com.entrepidea.java.kafka.samples.sample2.domain.Bar2;
import com.entrepidea.java.kafka.samples.sample2.domain.Foo1;
import com.entrepidea.java.kafka.samples.sample2.domain.Foo2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

//this class is a Kafka listener taking care of multiple topics.
@Component
@KafkaListener(id = "multiGroup", topics = { "foos", "bars" })
public class MultiMethods {

    private final TaskExecutor exec = new SimpleAsyncTaskExecutor();

    @KafkaHandler
    public void foo(@Payload Foo1 foo) {
        System.out.println("Received: " + foo);
        terminateMessage();
    }

    @KafkaHandler
    public void bar(@Payload Bar1 bar) {
        System.out.println("Received: " + bar);
        terminateMessage();
    }

    //this seems to be the gateway of all incoming messages - because the messages are byte stream, the other two methods
    //in this class, foo and bar, wouldn't be directly
    @KafkaHandler(isDefault = true)
    public void unknown(@Payload byte[] jsonData) throws IOException {
        System.out.println("Received incoming byte stream: " + jsonData);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        if(rootNode.has("bar")){
            Bar1 obj = objectMapper.treeToValue(rootNode, Bar1.class);
            bar(obj);
        }
        else if(rootNode.has("foo")){
            Foo1 obj = objectMapper.treeToValue(rootNode, Foo1.class);
            foo(obj);
        }
        terminateMessage();
    }

    private void terminateMessage() {
        this.exec.execute(() -> System.out.println("Hit Enter to terminate..."));
    }

}
