package com.entrepidea.spring.apps.WeatherReport.service.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherPojo {
    @SerializedName("apiVersion")
    @Expose
    private String apiVersion;
    @SerializedName("data")
    @Expose
    private WeatherData data;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public WeatherData getData() {
        return data;
    }

    public void setData(WeatherData data) {
        this.data = data;
    }
}
