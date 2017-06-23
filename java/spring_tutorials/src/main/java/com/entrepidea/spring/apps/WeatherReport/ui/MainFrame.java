package com.entrepidea.spring.apps.WeatherReport.ui;

import com.entrepidea.spring.apps.WeatherReport.service.WeatherService;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

/**
 * Created by jonat on 1/16/2017.
 */
public class MainFrame extends JFrame{

    @Autowired
    private EventBus eventBus;

    @Autowired
    private WeatherService weatherService;

    public MainFrame(){}

    public void initAndKickoff(){
        getContentPane().add(new MainPanel(weatherService));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
