package com.entrepidea.restful.tests;

import com.entrepidea.restful.tests.support.Country;
import com.entrepidea.restful.tests.support.RestResponse;
import com.entrepidea.restful.tests.support.Result;
import com.entrepidea.restful.tests.support.posts.NewsPost;
import com.entrepidea.restful.tests.support.posts.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


import java.io.IOException;
import java.util.List;


/**
 *the code is to test Restful ws APIs such as Spring's RestTemplate, GSON, Jackson, etc.
 *GSON and Jackson are used for data binding between JSON and Java POJO. A good tool to automatically construct a POJO based on JSON string is http://www.jsonschema2pojo.org/
 *
 * source: https://futurestud.io/tutorials/gson-getting-started-with-java-json-serialization-deserialization
 *
 * */
public class RestfulTests {

    Logger log = LoggerFactory.getLogger(RestfulTests.class);

	@Test
	public void gsonTest1() {
		RestTemplate rt = new RestTemplate();
		String url = "http://services.groupkt.com/country/get/all";
		ResponseEntity<String> resp = rt.getForEntity(url, String.class);
		assertEquals(resp.getStatusCode(), HttpStatus.OK);

		//log.info("response={}",resp.getBody());

        Gson gson = new GsonBuilder().create();
        Country c = gson.fromJson(resp.getBody(), Country.class);

        RestResponse rest = c.getRestResponse();

        List<String> msgs = rest.getMessages();
        for(String msg : msgs){
            log.info("message = {}", msg);
        }

        List<Result> results = rest.getResult();
        for(Result result: results){
            log.info("result: name={}, alphaCode1={}, alphaCode2={}", result.getName(), result.getAlpha2Code(), result.getAlpha3Code());
        }
	}


	@Test
    public void gsonTest2(){
	    RestTemplate rt = new RestTemplate();
	    String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=10";
	    ResponseEntity<String> resp = rt.getForEntity(url, String.class);
	    assertEquals(resp.getStatusCode(), HttpStatus.OK);

	    //log.debug("response = {}", resp.getBody());

	    Gson gson = new GsonBuilder().create();
        NewsPost np = gson.fromJson(resp.getBody(),NewsPost.class);
        List<Post> posts = np.getPosts();
        for(Post p : posts){
            log.info("id={}, author = {}", p.getId(), p.getAuthor().getName());
        }
    }


    public interface NewsPostsService {
        @GET("?json=get_category_posts&slug=news&count=")
        Call<NewsPost> listPosts(@Query("count") int count);
    }

    /*
    * This is a test of using retrofit2 framework to access RESTFul service, please google it for details.
     * Pay attention to the @Query annotation and the addConverterFactory to add Gson converter
    * */
    @Test
    public void retrofitTest(){
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
            for(Post p : posts.getPosts()) {
                log.debug("post id = {}, author = {} {}", p.getId(), p.getAuthor().getFirstName(), p.getAuthor().getLastName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
