
package com.entrepidea.java.concurrency.examples.cache.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category_ {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("parent")
    @Expose
    private int parent;
    @SerializedName("post_count")
    @Expose
    private int postCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category_ withId(int id) {
        this.id = id;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Category_ withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category_ withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category_ withDescription(String description) {
        this.description = description;
        return this;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public Category_ withParent(int parent) {
        this.parent = parent;
        return this;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public Category_ withPostCount(int postCount) {
        this.postCount = postCount;
        return this;
    }

}
