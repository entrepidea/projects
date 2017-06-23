package com.entrepidea.java.concurrency.examples.cache;

import com.entrepidea.java.concurrency.examples.cache.support.NewsPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonat on 4/16/2017.
 * Create a high performant and thread-safe cache
 */

interface Computable<K,V>{
    public V compute(K k);
}

interface NewsPostsService {
    @GET("?json=get_category_posts&slug=news&count=")
    Call<NewsPost> listPosts(@Query("count") int count);
}

/*
* For performant and thread-safe techniques, refer to
* this link: http://stackoverflow.com/questions/3636244/thread-safe-cache-of-one-object-in-java
* */
public abstract class Cache<K,V extends Object> implements Computable<K,V> {

    protected static Logger log = LoggerFactory.getLogger(Cache.class);


    private Map<K,V> buffer;

    public Cache(){
        if(buffer==null){
            buffer = new HashMap<>();
        }
    }
    @Override
    public V compute(K k) {
        if(buffer.containsKey(k)){
            return buffer.get(k);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://stacktips.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsPostsService service = retrofit.create(NewsPostsService.class);
        Call<NewsPost>  callPosts = service.listPosts(10);
        try {
            Response<NewsPost> resp = callPosts.execute();
            //log.debug(resp.body());
            NewsPost posts = resp.body();
            String key = "NEWS";
            buffer.put((K) key, (V) posts);
            return (V)posts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * Use the cache in an innocent cache - w/o thread-protection.
    * */
    public abstract NewsPost getNewsPosts();

    public void invalidate(){
        if(buffer!=null){
            buffer.clear();
            buffer = null;
        }
        buffer = new HashMap<>();
    }


}
