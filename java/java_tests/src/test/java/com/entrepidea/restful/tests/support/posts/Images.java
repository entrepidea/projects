
package com.entrepidea.restful.tests.support.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("full")
    @Expose
    private Full full;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("medium_large")
    @Expose
    private MediumLarge mediumLarge;
    @SerializedName("xlarge")
    @Expose
    private Xlarge xlarge;
    @SerializedName("featured")
    @Expose
    private Featured featured;
    @SerializedName("feed")
    @Expose
    private Feed feed;
    @SerializedName("lay-a")
    @Expose
    private LayA layA;
    @SerializedName("small")
    @Expose
    private Small small;

    public Full getFull() {
        return full;
    }

    public void setFull(Full full) {
        this.full = full;
    }

    public Images withFull(Full full) {
        this.full = full;
        return this;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Images withThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Images withMedium(Medium medium) {
        this.medium = medium;
        return this;
    }

    public MediumLarge getMediumLarge() {
        return mediumLarge;
    }

    public void setMediumLarge(MediumLarge mediumLarge) {
        this.mediumLarge = mediumLarge;
    }

    public Images withMediumLarge(MediumLarge mediumLarge) {
        this.mediumLarge = mediumLarge;
        return this;
    }

    public Xlarge getXlarge() {
        return xlarge;
    }

    public void setXlarge(Xlarge xlarge) {
        this.xlarge = xlarge;
    }

    public Images withXlarge(Xlarge xlarge) {
        this.xlarge = xlarge;
        return this;
    }

    public Featured getFeatured() {
        return featured;
    }

    public void setFeatured(Featured featured) {
        this.featured = featured;
    }

    public Images withFeatured(Featured featured) {
        this.featured = featured;
        return this;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Images withFeed(Feed feed) {
        this.feed = feed;
        return this;
    }

    public LayA getLayA() {
        return layA;
    }

    public void setLayA(LayA layA) {
        this.layA = layA;
    }

    public Images withLayA(LayA layA) {
        this.layA = layA;
        return this;
    }

    public Small getSmall() {
        return small;
    }

    public void setSmall(Small small) {
        this.small = small;
    }

    public Images withSmall(Small small) {
        this.small = small;
        return this;
    }

}
