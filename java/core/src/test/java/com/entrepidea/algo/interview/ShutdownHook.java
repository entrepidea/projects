package com.entrepidea.algo.interview;

/**
 * BNP Paribas onsite 02/18/20
 * What if the JVM crash? Can you salvage something before it crashes?
 *
 * */
//There is a Shutdown Hook to offer you a last chance...
public class ShutdownHook {
    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Clean up everything!");
        }));
        System.exit(0);
    }
}
