package com.entrepidea.imgurdnloadr;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }





    private static class Photo {
        String id;
        String title;
    }
    @Test
    public void testSyncFetchImgJson() throws JSONException {

        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        String url = "https://api.imgur.com/3/gallery/hot/viral/0.json";
        //String url  = "https://api.imgur.com/3/gallery/user/rising/0.json";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Client-ID 81ec752d0a85fe5")
                .header("User-Agent", "205254be2b1e517e3d379e895b7c6490432e69d6")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }




    @Test
    public void testAsycFetchImgJson() {

        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/gallery/user/rising/0.json")
                .header("Authorization","Client-ID 81ec752d0a85fe5")
                .header("User-Agent","205254be2b1e517e3d379e895b7c6490432e69d6")
                .build();


        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "An error has occurred " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // More code goes here
                System.out.println(response.body().string());
            }
        });
    }
}