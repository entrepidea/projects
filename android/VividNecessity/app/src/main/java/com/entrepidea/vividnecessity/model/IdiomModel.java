package com.entrepidea.vividnecessity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 3/1/2016.
 */
public class IdiomModel {
    @SerializedName("idioms")
    @Expose
    private List<Idiom> idioms = new ArrayList<Idiom>();

    /**
     *
     * @return
     * The idioms
     */
    public List<Idiom> getIdioms() {
        return idioms;
    }

    /**
     *
     * @param idioms
     * The idioms
     */
    public void setIdioms(List<Idiom> idioms) {
        this.idioms = idioms;
    }
}
