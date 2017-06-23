package com.entrepidea.spring.apps.WeatherReport.ui;

import com.entrepidea.spring.apps.WeatherReport.service.WeatherService;
import com.entrepidea.spring.apps.WeatherReport.service.domain.WeatherData;
import com.entrepidea.spring.apps.WeatherReport.service.domain.WeatherPojo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by jonat on 1/16/2017.
 */
public class MainPanel extends JPanel {

    private JToolBar toolBar;
    private JTextField textField;
    private JButton btnRun;
    private JTable table;


    private WeatherService weatherService;

    public MainPanel(WeatherService weatherService){
        super();

        this.weatherService = weatherService;

        setPreferredSize(new Dimension(500,300));
        setLayout(new BorderLayout());
        createToolBar();
        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(table = new JTable()), BorderLayout.CENTER);
    }


    //private method
    private void createToolBar(){
        toolBar = new JToolBar();
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(100,25));
        toolBar.add(textField);
        toolBar.add(new JLabel(" "));
        btnRun = new JButton("Run...");
        btnRun.addActionListener(e->{

            new SwingWorker<WeatherPojo,String>(){
                @Override
                protected WeatherPojo doInBackground() throws Exception {
                    return weatherService.getWeather(textField.getText());
                }

                @Override
                protected void process(List<String> chunks) {
                    super.process(chunks);
                }

                @Override
                protected void done() {
                    try {
                        WeatherPojo pojo = get();
                        WeatherData wd = pojo.getData();
                        Object[][] data = new Object[7][2];
                        String[] columns = new String[2];
                        columns[0]="Property";
                        columns[1] = "Value";
                        data[0][0] = "location"; data[0][1] = wd.getLocation();
                        data[1][0] = "temperature";data[1][1] = wd.getTemperature();
                        data[2][0] = "skytext";data[2][1] = wd.getSkytext();
                        data[3][0] = "humidity";data[3][1] = wd.getHumidity();
                        data[4][0] = "wind";data[4][1] = wd.getWind();
                        data[5][0] = "date";data[5][1] = wd.getDate();
                        data[6][0] = "day";data[6][1] = wd.getDay();

                        DefaultTableModel model = new DefaultTableModel();
                        model.setDataVector(data, columns);
                        table.setModel(model);

                    } catch (InterruptedException | ExecutionException e1) {
                        e1.printStackTrace();
                    }
                }
            }.execute();
        });
        toolBar.add(btnRun);
    }
}
