package middleware.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/*
* The code is from the book "Apache Kafka实战". To test it, best copy/paste into Linux env and run from there.
* Steps:
* 1. kick off Zookeeper and Kafka broker on Linux box "Evolution"
*   1.1 bin/zkServer.sh start conf/zk1.cfg
*   1.2 bin/kafka-server-start.sh config/server1.properties
* 2. Run "java -cp kafka_2.11-2.3.0/libs/slf4j-api-1.7.26.jar:kafka_2.11-2.3.0/libs/kafka-clients-2.3.0.jar:kafka_2.11-2.3.0/libs/log4j-1.2.17.jar:. Producer"
* 3. Run "java -cp kafka_2.11-2.3.0/libs/slf4j-api-1.7.26.jar:kafka_2.11-2.3.0/libs/kafka-clients-2.3.0.jar:kafka_2.11-2.3.0/libs/log4j-1.2.17.jar:. Consumer"
*
* Note: kafka_2.11-2.3.0 need to present as the supporting library.
*
* Date: 10/09/2019
*
* */

public class Producer {
    public static void main(String[] args){
        Properties pros = new Properties();
        pros.put("bootstrap.servers", "evolution:9092");
        pros.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        pros.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        pros.put("acks","-1");
        pros.put("retries",3);
        pros.put("batch.size",10);
        pros.put("linger.ms",10);
        pros.put("buffer.memory",33554432);
        pros.put("max.block.ms",3000);

        org.apache.kafka.clients.producer.Producer<String,String> producer = new KafkaProducer<>(pros);
        for(int i=0;i<100;i++){
            producer.send(new ProducerRecord<>("test-topic", Integer.toString(i),Integer.toString(i)));
        }
        producer.close();

    }
}
