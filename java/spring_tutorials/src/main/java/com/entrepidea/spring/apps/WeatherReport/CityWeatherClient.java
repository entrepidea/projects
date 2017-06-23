package com.entrepidea.spring.apps.WeatherReport;

import com.entrepidea.spring.apps.WeatherReport.ui.MainFrame;
import com.entrepidea.spring.apps.WeatherReport.ui.MainPanel;
import com.entrepidea.spring.core.ioc.bootstraps.supports.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

/**
 * Ths is simple applications that serves as a restful service's client.
 * The Restful service is: http://weathers.co/api.php?city=New+York
 * This application uses Swing as GUI, Spring's annotation supported IOC framework
 *
 * Created by jonat on 1/16/2017.
 */
public class CityWeatherClient  {

    public CityWeatherClient(){

    }

    private static void createAndShowUI(final MainFrame frame){
        frame.initAndKickoff();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.entrepidea.spring.apps.WeatherReport.config.SpringConfig");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(clazz);
        //or you can simple bootstrap the container like below, in this case you don't call #refresh,
        //as it's already done implicitly
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(clazz);
        context.registerShutdownHook();
        context.refresh();

        final MainFrame frame = (MainFrame)context.getBean(MainFrame.class);
        SwingUtilities.invokeLater(()->{
            createAndShowUI(frame);
        });
    }
}
