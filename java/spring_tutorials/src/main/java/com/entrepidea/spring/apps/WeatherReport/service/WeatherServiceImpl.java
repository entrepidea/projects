package com.entrepidea.spring.apps.WeatherReport.service;

import com.entrepidea.spring.apps.WeatherReport.service.domain.WeatherPojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jonat on 1/16/2017.
 */
public class WeatherServiceImpl implements WeatherService {
    @Override
    public WeatherPojo getWeather(String city) {
        RestTemplate rt = new RestTemplate();
        String url = "http://weathers.co/api.php?city="+city;
        ResponseEntity<String> resp = rt.getForEntity(url, String.class);
        Gson gson = new GsonBuilder().create();
        WeatherPojo pojo = (WeatherPojo) gson.fromJson(resp.getBody(),WeatherPojo.class);
        return pojo;
    }
}
