package com.entrepidea.twitter;

import twitter4j.*;

public class GetAccountSettings {
    /**
     * Usage: java twitter4j.examples.account.GetAccountSettings
     *
     * @param args arguments doesn't take effect with this example
     */
    public static void main(String[] args) {
        try {
            //Twitter twitter = new TwitterFactory().getInstance();
            Twitter twitter = TwitterFactory.getSingleton();
            AccountSettings settings = twitter.getAccountSettings();
            System.out.println("Sleep time enabled: " + settings.isSleepTimeEnabled());
            System.out.println("Sleep end time: " + settings.getSleepEndTime());
            System.out.println("Sleep start time: " + settings.getSleepStartTime());
            System.out.println("Geo enabled: " + settings.isGeoEnabled());
            System.out.println("Screen name: " + settings.getScreenName());
            System.out.println("Listing trend locations:");
            Location[] locations = settings.getTrendLocations();
            for (Location location : locations) {
                System.out.println(" " + location.getName());
            }
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get account settings: " + te.getMessage());
            System.exit(-1);
        }
    }
}