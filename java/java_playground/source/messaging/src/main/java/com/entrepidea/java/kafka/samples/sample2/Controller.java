package com.entrepidea.java.kafka.samples.sample2;

import com.entrepidea.java.kafka.samples.sample2.domain.Bar1;
import com.entrepidea.java.kafka.samples.sample2.domain.Foo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableKafka
public class Controller {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private RecordMessageConverter recordMessageConverter;

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        kafkaTemplate.setMessageConverter(recordMessageConverter);
        this.kafkaTemplate.send("foos", new Foo1(what));
    }

    @PostMapping(path = "/send/bar/{what}")
    public void sendBar(@PathVariable String what) {
        kafkaTemplate.setMessageConverter(recordMessageConverter);
        this.kafkaTemplate.send("bars", new Bar1(what));
    }

    @PostMapping(path = "/send/unknown/{what}")
    public void sendUnknown(@PathVariable String what) {
        this.kafkaTemplate.send("bars", what);
    }

}
