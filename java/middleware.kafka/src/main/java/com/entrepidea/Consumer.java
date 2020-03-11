package com.entrepidea;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/*
* See the note in Producer.java
* @Date: 10/09/2019
* */
public class Consumer {
    public static void main(String[] args){
        Properties pros = new Properties();
        pros.put("bootstrap.servers","evolution:9092");
        pros.put("group.id","test-group");
        pros.put("enable.auto.commit", "true");
        pros.put("auto.commit.interval.ms","1000");
        pros.put("auto.offset.reset","earliest");
        pros.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        pros.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(pros);
        consumer.subscribe(Arrays.asList("test-topic"));
        try{
            while(true){
                ConsumerRecords<String,String> records = consumer.poll(1000);
                for(ConsumerRecord<String,String> rec: records){
                    System.out.printf("offset = %d, key = %s, value=%s%n",
                            rec.offset(),rec.key(),rec.value());
                }
            }
        }
        finally {
            consumer.close();
        }


    }
}
