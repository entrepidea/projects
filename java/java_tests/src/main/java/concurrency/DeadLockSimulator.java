package concurrency;

//Below is a simple simulator of how a dead lock is generated,
//Interviewers like to ask how you detect a deadlock, sometime, they asked for
//least invasive way of creating thread dump, especially in a production environment, where no re-run or shutdown is allowed.
//Tools like jstack, Jconsole, virtual VM, Java Misssion Control, "kill -3" etc are used to generate thread dump
//some don't interrupt the running of the application thus is least intrusive.
//@reference: https://coderanch.com/t/699090/java/Deadlock-Detection-Production-Environment
//https://blog.fastthread.io/2016/06/06/how-to-take-thread-dumps-7-options/
//it's very easy to play with the code below.
public class DeadLockSimulator {
    public static Object Lock1 = new Object();
    public static Object Lock2 = new Object();


    private static class FirstThread extends Thread {
        public void run() {
            synchronized (Lock1) {
                System.out.println("Thread 1: Holding lock 1...");
                try { Thread.sleep(10); } catch (Exception e) {}
                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (Lock2) {
                    System.out.println("Thread 1: Holding lock 1 & 2...");
                }
            }
        }
    }

    private static class SecondThread extends Thread {
        public void run() {
            synchronized (Lock2) {
                System.out.println("Thread 2: Holding lock 2...");
                try { Thread.sleep(10); } catch (Exception e) {}
                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (Lock1) {
                    System.out.println("Thread 2: Holding lock 1 & 2...");
                }
            }
        }
    }

    public static void main(String args[]) {

        new FirstThread().start();
        new SecondThread().start();
    }
}
