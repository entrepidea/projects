package com.entrepidea.interview;

/**
 * BNP Paribas, 02/18/20, onsite
 * Two types of Threads? Difference b/w Daemon v.s Non-daemon?
 *
 * */
//Once the main thread exists, Daemon threads exits too, non-daemon ones continue to finish its running course.
//It's not specifically about when the main thread exits, but rather when all non-daemon threads exit.
// Since you create another non-daemon thread (i.e. t1) that sleeps for the same amount of time as your daemon thread (i.e. t2),
// the daemon thread still has time to execute before the JVM terminates
//Reference: https://stackoverflow.com/questions/61257685/is-a-daemon-thread-supposed-to-exit-the-same-time-as-the-main-thread-does?noredirect=1#comment108370319_61257685
public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(1500);
                System.out.println("Daemon thread, but you won't see this line, because main thread exits, so does this thread.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.setDaemon(true);
        t1.start();

        Thread t2 = new Thread(()->{
            try {
                Thread.sleep(500);
                System.out.println("You will see me even after the main thread exists.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.setDaemon(false);
        t2.start();

        Thread.sleep(200); //Main thread
        System.out.println("Main thread exists");
    }
}
