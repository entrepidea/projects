package com.entrepidea.spring.rest.supports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Idiom implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("dateCreated")
    @Expose
    private Integer dateCreated;
    @SerializedName("formattedDate")
    @Expose
    private String formattedDate;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("example")
    @Expose
    private String example;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The subtitle
     */
    public String getSubtitle() {
        int idx = subtitle.indexOf("EXAMPLE");
        if(idx>0) {
            return subtitle.substring(0, idx);
        }
        else {
            return subtitle;
        }
    }

    /**
     *
     * @param subtitle
     * The subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     *
     * @return
     * The dateCreated
     */
    public Integer getDateCreated() {
        return dateCreated;
    }

    /**
     *
     * @param dateCreated
     * The dateCreated
     */
    public void setDateCreated(Integer dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     *
     * @return
     * The formattedDate
     */
    public String getFormattedDate() {
        return formattedDate;
    }

    /**
     *
     * @param formattedDate
     * The formattedDate
     */
    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    /**
     *
     * @return
     * The definition
     */
    public String getDefinition() {
        return definition;
    }

    /**
     *
     * @param definition
     * The definition
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     *
     * @return
     * The example
     */
    public String getExample() {
        return example;
    }

    /**
     *
     * @param example
     * The example
     */
    public void setExample(String example) {
        this.example = example;
    }


}
