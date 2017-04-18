
package com.entrepidea.java.concurrency.examples.cache.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewsPost  implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("pages")
    @Expose
    private int pages;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NewsPost withStatus(String status) {
        this.status = status;
        return this;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public NewsPost withCount(int count) {
        this.count = count;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public NewsPost withPages(int pages) {
        this.pages = pages;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public NewsPost withCategory(Category category) {
        this.category = category;
        return this;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public NewsPost withPosts(List<Post> posts) {
        this.posts = posts;
        return this;
    }

}
