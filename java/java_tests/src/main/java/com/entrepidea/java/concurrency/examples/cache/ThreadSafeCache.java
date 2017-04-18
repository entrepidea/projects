package com.entrepidea.java.concurrency.examples.cache;

import com.entrepidea.java.concurrency.examples.cache.support.NewsPost;
import com.entrepidea.java.concurrency.examples.cache.support.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonat on 4/16/2017.
 */
public class ThreadSafeCache extends Cache{

    private static Logger log = LoggerFactory.getLogger(ThreadSafeCache.class);

    //singleton
    static class Foo {
        private static ThreadSafeCache instance = new ThreadSafeCache();
    }
    public static ThreadSafeCache instance(){
        ThreadSafeCache ins = Foo.instance;
        return ins;
    }
    //end of constructing singleton

    //Simple and brutal synchronization to achieve thread-safety
    @Override
    public NewsPost getNewsPosts() {
       synchronized(this){
           return (NewsPost) compute("NEWS");
       }
    }

    public static void main(String[] args) {

        Cache<String, Object> cache = ThreadSafeCache.instance();

        cache.invalidate();

        log.info("Testing set #2");
        long start = System.nanoTime();
        cache.getNewsPosts();
        log.info("it take {} nano seconds to retrieve the data before caching in a thread-safe way.", (System.nanoTime() - start));

        start = System.nanoTime();
        NewsPost posts = cache.getNewsPosts();
        log.info("it take {} nano seconds to retrieve the data after caching in a thread-safe way.", (System.nanoTime() - start));

        for (Post post : posts.getPosts()) {
            log.info("id={}", post.getId());
        }
    }
}
