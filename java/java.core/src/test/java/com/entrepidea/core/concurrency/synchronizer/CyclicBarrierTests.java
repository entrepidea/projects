package com.entrepidea.core.concurrency.synchronizer;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;
/**
 * @Desc:
 * CyclicBarrier set a barrier for all participating threads before they are launched.
 * The difference b/w it and CountDownLatch is that CyclicBarrier's threshold can be reused multiple times.
 * In the Soldiers example below, we use CyclicBarrier to set a barrier to wait for all soldiers to assemble, and another barrier to wait all the tasks have been performed.
 * An action can be setup and run when CyclicBarrier's threshold is reached.
 *
 * @Source: multiple sources, see the comments in the code.
 *
 * @Date: 01/08/20
 *
 * */
public class CyclicBarrierTests {

    //Example #1. from: 实战Java高并发程序设计, 3.1.6
    public static class SoldierAction implements Runnable{
        private CyclicBarrier barrier;
        private String name;
        public SoldierAction(CyclicBarrier barrier, String name){
            this.barrier = barrier;
            this.name = name;
        }
        @Override
        public void run(){
            try {
                System.out.println(name+" waiting for others.");
                barrier.await(); //wait until all soldiers are assembled.
                doWork();
                barrier.await();
            }
            catch(InterruptedException | BrokenBarrierException e){
                e.printStackTrace();
            }
        }

        private void doWork(){
            try{
                System.out.println(name+" is on the job...");
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
                System.out.println(name+" has finished his/her task");
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static class BarrierRun implements Runnable{
        private int nTasks;
        private boolean flag;
        public BarrierRun(int n, boolean f){
            nTasks = n;
            flag = f;
        }
        @Override
        public void run(){
            if(flag){
                System.out.println(nTasks+ "tasks has been completed.");
            }
            else{
                System.out.println("Get ready to kick of "+nTasks+" tasks");
                flag = true;
            }
        }
    }

    @Test
    public void test()  {
        final int n = 10;
        boolean flag = false;
        ExecutorService es = Executors.newCachedThreadPool();
        CyclicBarrier barrier = new CyclicBarrier(n, new BarrierRun(n,flag));
        for(int i=0;i<n;i++){
            System.out.println("Soldier#"+i+" report to duty!");
            es.submit(new SoldierAction(barrier, "Soldier#"+i));
        }
        try {
            es.awaitTermination(20, TimeUnit.SECONDS);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
