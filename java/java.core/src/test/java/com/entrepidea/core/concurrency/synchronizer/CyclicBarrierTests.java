package com.entrepidea.core.concurrency.synchronizer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;
/**
 * @Desc:
 * CyclicBarrier set a barrier for all participating threads before they are launched.
 * The differences b/w it and CountDownLatch are:
 *      1. CountDownLatch can specify a thread as the floor gate (barrier);
 *      2. CyclicBarrier can reuse the existing threads; the thread in cyclicBarrier are more designed as uniform and in a group; and CyclicBarrier can insert another customized thread for its own use.
 *
 * @Source: multiple sources, see the comments in the code.
 *
 * @Note: it's from interview "BGC phone interview, 12/02/19"
 *
 * @Date: 01/08/20, update: 09/18/21
 *
 * */
public class CyclicBarrierTests {

    //  Example #1. from: 实战Java高并发程序设计, 3.1.6
    //  In the Soldiers example below, we use CyclicBarrier to set a barrier to wait for all soldiers to assemble, and another barrier to wait all the tasks have been performed.
    //  An action can be setup and run when CyclicBarrier's threshold is reached.
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


    //example#2: Besides the blocking-until-all-arrived feature that is similar to that of CountDownLatcher, there are two more features of CyclicBarrier's own:
    //recycle of those thread in the group; and embed a loopback Runnable (specified in CyclicBarrier's constructor as 2nd parameter).
    //resource: https://zhuanlan.zhihu.com/p/241985135
    //note: in the link above, the article also includes the analysis of source code.
    //date: 09/18/21
    @Test
    public void test2(){

        final int n = 5;
        CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("执行一次 Runnable ");
            }
        });
        ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNameFormat("Thread-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {

            pool.submit(() -> {

                try {
                    System.out.println(Thread.currentThread().getName() + " 开始执行");
                    CYCLIC_BARRIER.await();

                    System.out.println(Thread.currentThread().getName() + " 冲破屏障 >>> 1");
                    CYCLIC_BARRIER.await();

                    System.out.println(Thread.currentThread().getName() + " 冲破屏障 >>>>> 2");
                    CYCLIC_BARRIER.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            });

        }

        pool.shutdown();

    }
}
