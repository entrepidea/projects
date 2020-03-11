package com.entrepidea.core.collection.list;

import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc: In Wells Fargo's on-site interview (12/03/19), I was required to write an immutable Book class
 * public class Book {
 *  List<Page> pages
 * }
 * To return a list of Page and make sure it's immutable, we can use CopyOnWriteArrayList, which try to prevent structural change.
 * This should be the right direction. See my email for more.
 *
 * However, this is the idea of COW array list: when traversal operations outnumber mutative operations, some leeways are allowed to make a copy of underlying
 * arrayList, even though it's costly. The iterator of the newly created copy is the "snapshot" of the underlying array at the time when iterator is created.
 *
 *
 * @Source: official doc and all articles from Zhihu: https://www.zhihu.com/search?type=content&q=CopyOnWriteArrayList,
 *
 * */
public class CopyOnWriteArrayListTests {

    final Logger logger = LoggerFactory.getLogger(CopyOnWriteArrayListTests.class);
    //TODO think up some test cases using COW arrayList?

    /*
    * The test below will prove that once an iterator is created from COW, the change of underlying array won't have effect on it.
    * https://zhuanlan.zhihu.com/p/59601301
    * */
    @Test
    public void test(){
        List<String> cowList = new CopyOnWriteArrayList<>(new String[]{"ABC","EFG"});
        Iterator<String> iter = cowList.iterator();
        new Thread(()->{
            cowList.remove(0);
        }).start();
        while(iter!=null && iter.hasNext()){
            logger.info(iter.next()); //this works as if removal never happens!
        }
        try {
            Thread.sleep(3000);
        }
        catch(InterruptedException e){
        }

        iter = cowList.iterator();
        logger.info("OKie Dokie, now the new snapshot of underlying array: ");
        while(iter!=null && iter.hasNext()){
            logger.info(iter.next()); //and now, iterator reflects a new snapshot of underlying array, which has only one member inside now
        }
    }
}
