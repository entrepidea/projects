package com.entrepidea.java.concurrency.cache;

import com.entrepidea.java.concurrency.cache.support.NewsPost;
import com.entrepidea.java.concurrency.cache.support.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonat on 4/16/2017.
 */
public class InnocentCache extends Cache{

    private static Logger log = LoggerFactory.getLogger(InnocentCache.class);


    public static void main(String[] args){
        Cache<String, Object> cache = new InnocentCache();

        log.info("Testing set #1");
        long start = System.nanoTime();
        cache.getNewsPosts();
        log.info("it take {} nano seconds to retrieve the data before caching.", (System.nanoTime() - start));

        start = System.nanoTime();
        NewsPost posts = cache.getNewsPosts();
        log.info("it take {} nano seconds to retrieve the data after caching.", (System.nanoTime() - start));

        for(Post post : posts.getPosts()){
            log.info("id={}",post.getId());
        }
    }

    @Override
    public NewsPost getNewsPosts() {
            return (NewsPost) compute("NEWS");
    }
}
