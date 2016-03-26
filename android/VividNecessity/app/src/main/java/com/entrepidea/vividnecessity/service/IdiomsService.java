package com.entrepidea.vividnecessity.service;

import com.entrepidea.vividnecessity.model.IdiomModel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by john on 3/1/2016.
 */
public interface IdiomsService {
    public static String URL = "http://entrepidea.com:8080/ws/Vivid.Necessity/allIdioms";
    @GET("/")
    void getAllIdioms(Callback<IdiomModel> cb);
}
