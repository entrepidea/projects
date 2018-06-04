package com.entrepidea.restful.tests.support;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class RequestHandler
{
    private OkHttpClient okHttpClient;

    public RequestHandler(OkHttpClient okHttpClient)
    {
        this.okHttpClient = okHttpClient;
    }

    public String request(Request request) throws IOException
    {
        Response response = okHttpClient.newCall(request).execute();
        String body = response.body().string();

        if (response.isSuccessful())
        {
            return body;
        } else
        {
            throw new RuntimeException(response.message());
        }
    }
}