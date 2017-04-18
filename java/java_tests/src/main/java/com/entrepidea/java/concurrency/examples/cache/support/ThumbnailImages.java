
package com.entrepidea.java.concurrency.examples.cache.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThumbnailImages {

    @SerializedName("full")
    @Expose
    private Full_ full;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail_ thumbnail;
    @SerializedName("medium")
    @Expose
    private Medium_ medium;
    @SerializedName("medium_large")
    @Expose
    private MediumLarge_ mediumLarge;
    @SerializedName("xlarge")
    @Expose
    private Xlarge_ xlarge;
    @SerializedName("featured")
    @Expose
    private Featured_ featured;
    @SerializedName("feed")
    @Expose
    private Feed_ feed;
    @SerializedName("lay-a")
    @Expose
    private LayA_ layA;
    @SerializedName("small")
    @Expose
    private Small_ small;

    public Full_ getFull() {
        return full;
    }

    public void setFull(Full_ full) {
        this.full = full;
    }

    public ThumbnailImages withFull(Full_ full) {
        this.full = full;
        return this;
    }

    public Thumbnail_ getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail_ thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ThumbnailImages withThumbnail(Thumbnail_ thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public Medium_ getMedium() {
        return medium;
    }

    public void setMedium(Medium_ medium) {
        this.medium = medium;
    }

    public ThumbnailImages withMedium(Medium_ medium) {
        this.medium = medium;
        return this;
    }

    public MediumLarge_ getMediumLarge() {
        return mediumLarge;
    }

    public void setMediumLarge(MediumLarge_ mediumLarge) {
        this.mediumLarge = mediumLarge;
    }

    public ThumbnailImages withMediumLarge(MediumLarge_ mediumLarge) {
        this.mediumLarge = mediumLarge;
        return this;
    }

    public Xlarge_ getXlarge() {
        return xlarge;
    }

    public void setXlarge(Xlarge_ xlarge) {
        this.xlarge = xlarge;
    }

    public ThumbnailImages withXlarge(Xlarge_ xlarge) {
        this.xlarge = xlarge;
        return this;
    }

    public Featured_ getFeatured() {
        return featured;
    }

    public void setFeatured(Featured_ featured) {
        this.featured = featured;
    }

    public ThumbnailImages withFeatured(Featured_ featured) {
        this.featured = featured;
        return this;
    }

    public Feed_ getFeed() {
        return feed;
    }

    public void setFeed(Feed_ feed) {
        this.feed = feed;
    }

    public ThumbnailImages withFeed(Feed_ feed) {
        this.feed = feed;
        return this;
    }

    public LayA_ getLayA() {
        return layA;
    }

    public void setLayA(LayA_ layA) {
        this.layA = layA;
    }

    public ThumbnailImages withLayA(LayA_ layA) {
        this.layA = layA;
        return this;
    }

    public Small_ getSmall() {
        return small;
    }

    public void setSmall(Small_ small) {
        this.small = small;
    }

    public ThumbnailImages withSmall(Small_ small) {
        this.small = small;
        return this;
    }

}
