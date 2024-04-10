package com.entrepidea.java.kafka.samples.sample1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrepidea.java.kafka.samples.sample1.domain.Foo;
/**
 * @author Gary Russell
 * @since 2.2.1
 */
@RestController
public class Controller {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        this.kafkaTemplate.send("topic1", new Foo(what));
    }

}