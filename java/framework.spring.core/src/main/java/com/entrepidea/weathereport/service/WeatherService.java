package com.entrepidea.weathereport.service;

import com.entrepidea.weathereport.service.domain.WeatherPojo;

/**
 * Created by jonat on 1/16/2017.
 */
public interface WeatherService {
    WeatherPojo getWeather(String city);
}
