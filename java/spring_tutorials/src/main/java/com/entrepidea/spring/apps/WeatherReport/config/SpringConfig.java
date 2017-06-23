package com.entrepidea.spring.apps.WeatherReport.config;

import com.entrepidea.spring.apps.WeatherReport.service.WeatherService;
import com.entrepidea.spring.apps.WeatherReport.service.WeatherServiceImpl;
import com.entrepidea.spring.apps.WeatherReport.ui.MainFrame;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This serves as spring's configuration file - like the traditional xml configuration file
 * Created by jonat on 1/16/2017.
 */
@Configuration
@ComponentScan(basePackages="com.entrepidea.spring.apps.WeatherReport")
public class SpringConfig {

    @Bean
    public EventBus newEventBus(){
        return new EventBus();
    }

    @Bean
    public MainFrame newFrame(){
        return new MainFrame();
    }
    @Bean
    public WeatherService newService(){
        return new WeatherServiceImpl();
    }

}
