package com.entrepidea.twitter;

import twitter4j.*;

public class Search4Twitters {

    public static void main(String... args) throws TwitterException {
        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("Bill Gates");
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
    }
}
