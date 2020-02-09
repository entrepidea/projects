package com.entrepidea.weathereport.service.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jonat on 1/16/2017.
 */


public class WeatherData {

    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("skytext")
    @Expose
    private String skytext;
    @SerializedName("humidity")
    @Expose
    private String humidity;
    @SerializedName("wind")
    @Expose
    private String wind;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("day")
    @Expose
    private String day;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSkytext() {
        return skytext;
    }

    public void setSkytext(String skytext) {
        this.skytext = skytext;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
