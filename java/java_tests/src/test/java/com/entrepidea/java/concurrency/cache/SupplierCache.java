package com.entrepidea.java.concurrency.cache;

import com.entrepidea.java.concurrency.cache.support.NewsPost;
import com.entrepidea.java.concurrency.cache.support.Post;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * Created by jonat on 4/17/2017.
 * This takes advantage of Guava's Supplier class, which is thread-safe.
 * http://stackoverflow.com/questions/3636244/thread-safe-cache-of-one-object-in-java
 */
public class SupplierCache extends Cache {

    static class Foo{
        private static SupplierCache instance = new SupplierCache();
    }

    public static SupplierCache instance(){
        return Foo.instance;
    }
    private Supplier<NewsPost> supplier = new Supplier<NewsPost>(){
        public NewsPost get(){
            return (NewsPost) compute("NEWS");
        }
    };

    // volatile reference so that changes are published correctly see invalidate()
    private volatile Supplier<NewsPost> memorized = Suppliers.memoize(supplier);

    @Override
    public NewsPost getNewsPosts() {
        return memorized.get();
    }

    public void invalidate(){
        memorized = Suppliers.memoize(supplier);
    }

    public static void main(String[] args){

        Cache<String, Object> cache = SupplierCache.instance();

        cache.invalidate();

        log.info("Testing set #3");
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
