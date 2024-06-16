package com.entrepidea.java.kafka.samples.sample3;

import com.entrepidea.java.kafka.samples.sample3.domain.Foo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gary Russell
 * @since 2.2.1
 */
@RestController
public class Controller {
    @Autowired
    private KafkaTemplate<String, Object> template;

    @PostMapping(path = "/send/foos/{what}")
    public void sendFoo(@PathVariable String what) {
        this.template.executeInTransaction(kafkaTemplate -> {
            StringUtils.commaDelimitedListToSet(what).stream()
                    .map(Foo1::new)
                    .forEach(foo -> kafkaTemplate.send("topic2", foo));
            return null;
        });
    }
}
