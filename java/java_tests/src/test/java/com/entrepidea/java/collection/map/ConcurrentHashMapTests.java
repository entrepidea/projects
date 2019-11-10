package com.entrepidea.java.collection.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * ConcurrentHashMap is a better alternative to SychronizedMap. Prior to 1.8 the technique to achieve threadsafety used is segmentation & lockstripping. Since 1.8, it adopts CAS for the same purpose.
 * https://zhuanlan.zhihu.com/p/63629645?utm_source=com.ideashower.readitlater.pro&utm_medium=social&utm_oi=809364293245075456
 *
 *
 * Created by jonat on 10/23/2019.
 */
public class ConcurrentHashMapTests {


    //performance tests between synchronized map and concurrentHashMap
    //This could be done with a better test.
    //The idea is from this post: https://crunchify.com/hashmap-vs-concurrenthashmap-vs-synchronizedmap-how-a-hashmap-can-be-synchronized-in-java/
    private void perf(Map<String, Integer> map, int threadNum){

        long start = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        for(int i=0;i<threadNum;i++) {
            service.execute(() -> {
                for(int j=0;j<5000000;j++){
                    Integer value = (int)(Math.ceil(Math.random()));
                    String key = String.valueOf(value);
                    map.put(key,value);
                }
            });
        }
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        long end = System.nanoTime();
        System.out.println("Total time takes: "+(end-start)/1000000L+" milliseconds for "+map.getClass());

    }
    @Test
    public void testMapPerformance(){
        final int THREAD_NUM = 5;

        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String,Integer>());
        perf(map,THREAD_NUM);
        map = new ConcurrentHashMap<>();
        perf(map,THREAD_NUM);

    }

}
