package com.entrepidea.experiments.twitter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * @Desc: This shows how to dump tweets to Kafka. The original code is from: https://www.tutorialspoint.com/apache_kafka/apache_kafka_real_time_application.htm
 *
 * @Date: 03/31/20
 *
 * */
public class KafkaTwitterProducer {
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<Status> queue = new LinkedBlockingQueue<>(1000);

        String consumerKey = "qNtZOgLp4Q4C23j0a0ADSshFP";
        String consumerSecret="4x0o8s719G2cQqHfFoPTMpPcQQjJkLIgToLZvuFhCUUA34AXwf";
        String accessToken = "837148831183699968-9xlUK5OUc5Z48FjrZO8SFEQ1hUTBcEl";
        String accessTokenSecret = "54allOL5ZEsSzbPlfsFxUX7RU7OcdY8pto38HhoFK9gF3";
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        //TwitterStream twitterStream = TwitterStreamFactory.getSingleton();

        StatusListener listener = new StatusListener() {

            @Override
            public void onStatus(Status status) {
                queue.offer(status);

                System.out.println("@" + status.getUser().getScreenName()+ " - " + status.getText());
                System.out.println("@" + status.getUser().getScreenName());

                for(URLEntity urle : status.getURLEntities()) {
                    System.out.println(urle.getDisplayURL());
                }

                for(HashtagEntity hashtage : status.getHashtagEntities()) {
                    System.out.println(hashtage.getText());
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                // System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                // System.out.println("Got scrub_geo event userId:" + userId +"upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                // System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        String keyWords = "coronavirus";
        FilterQuery query = new FilterQuery().track(keyWords);
        twitterStream.filter(query);

        Thread.sleep(5000);

        //Add Kafka producer config settings
        Properties props = new Properties();
        props.put("bootstrap.servers", "evolution:9092,competition:9092,kali:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        int i = 0;
        int j = 0;

        final String topicName = "coronavirus";

        while(i < 10) {
            Status ret = queue.poll();

            if (ret == null) {
                Thread.sleep(100);
                i++;
            }else {
                for(HashtagEntity hashtage : ret.getHashtagEntities()) {
                    System.out.println("Hashtag: " + hashtage.getText());
                    producer.send(new ProducerRecord(topicName, Integer.toString(j++), hashtage.getText()));
                }
            }
        }
        producer.close();
        Thread.sleep(5000);
        twitterStream.shutdown();
    }
}
