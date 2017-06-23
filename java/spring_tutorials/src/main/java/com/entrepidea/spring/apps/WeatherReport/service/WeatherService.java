package com.entrepidea.spring.apps.WeatherReport.service;

import com.entrepidea.spring.apps.WeatherReport.service.domain.WeatherPojo;

/**
 * Created by jonat on 1/16/2017.
 */
public interface WeatherService {
    WeatherPojo getWeather(String city);
}
