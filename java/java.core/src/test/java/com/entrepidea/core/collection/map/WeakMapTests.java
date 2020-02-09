package com.entrepidea.core.collection.map;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Desc:
 * The below checkBalancedBinaryTree demo the WeakHashMap. WeakHashMap will be the first candidates for garbage collection. GC will
 * clean up the keys and values in such a map when it detects a low memory,before it throws a OutOfMemory error.
 * The below code take every 3rd elements out from the map and put them into a list, factually making them strongly reference,
 * so they won't be garbage collected.
 *
 * @Source: Think in Java, 4th edition, p892, "The WeakHashMap"
 *
 *
 * */


public class WeakMapTests {
    private static Logger log = LoggerFactory.getLogger(HashMapTests.class);

    class Element {

        private String ident;

        public Element(String id){
            this.ident = id;
        }
        @Override
        public String toString(){
            return ident;
        }
        @Override
        public boolean equals(Object o){
            return o instanceof Element &&
                    ident.equals(((Element)o).ident);
        }
        @Override
        public int hashCode(){
            return ident.hashCode();
        }
        @Override
        protected void finalize(){
            log.info("Finalizing "+getClass().getSimpleName() + " "+ident);
        }
    }

    class Key extends Element{

        public Key(String id) {
            super(id);
        }
    }

    class Value extends Element{

        public Value(String id) {
            super(id);
        }
    }

    //There are two approaches to configure log4j, the one below is the easier one.
    //https://stackoverflow.com/questions/12532339/no-appenders-could-be-found-for-loggerlog4j
    @Before
    public void setup(){
        BasicConfigurator.configure();
    }
    @Test
    public void testCleanWeakHashMap(){
        int size = 100;
        //Key[] keys = new Key[size];
        List<Key> keys = new ArrayList<>();

        WeakHashMap<Key, Value> map = new WeakHashMap<Key,Value>();

        for(int i=0;i<size;i++){
            Key key = new Key(new Integer(i).toString());
            Value value = new Value(new Integer(i).toString());

            if(i%3==0){
                keys.add(key);
            }
            map.put(key, value);
        }

        Set<Key> set = map.keySet();

        int count=0;
        for(Iterator<Key> iter = set.iterator();iter.hasNext();){
            Key k = iter.next();
            log.info(count+ " "+k +" : "+ map.get(k));
            count++;
        }
        log.info("start gc: ");
        long then = System.nanoTime();
        System.gc();
        log.info("gc done, it takes : {} nano seconds.",(System.nanoTime()-then));

        log.info("survivor numbers: {} ",keys.size());

        for(Key k: keys){
            log.info("survivors: {} ", k);
        }
    }
}
