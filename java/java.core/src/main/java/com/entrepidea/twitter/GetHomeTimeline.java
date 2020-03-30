package com.entrepidea.twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.util.List;

public class GetHomeTimeline {
    public static void main(String... args) throws TwitterException {
       /* ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("qNtZOgLp4Q4C23j0a0ADSshFP")
                .setOAuthConsumerSecret("4x0o8s719G2cQqHfFoPTMpPcQQjJkLIgToLZvuFhCUUA34AXwf")
                .setOAuthAccessToken("837148831183699968-9xlUK5OUc5Z48FjrZO8SFEQ1hUTBcEl")
                .setOAuthAccessTokenSecret("54allOL5ZEsSzbPlfsFxUX7RU7OcdY8pto38HhoFK9gF3");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();*/
        Twitter twitter = TwitterFactory.getSingleton();
        List<Status> statuses = twitter.getHomeTimeline();
        System.out.println("Showing home timeline:");
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" +
                    status.getText());
        }
    }
}
