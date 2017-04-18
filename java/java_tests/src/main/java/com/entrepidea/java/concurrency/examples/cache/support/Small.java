
package com.entrepidea.java.concurrency.examples.cache.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Small {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private int width;
    @SerializedName("height")
    @Expose
    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Small withUrl(String url) {
        this.url = url;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Small withWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Small withHeight(int height) {
        this.height = height;
        return this;
    }

}
