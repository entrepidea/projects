
package com.entrepidea.java.concurrency.tests.cache.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomFields {

    @SerializedName("dsq_thread_id")
    @Expose
    private List<String> dsqThreadId = null;

    public List<String> getDsqThreadId() {
        return dsqThreadId;
    }

    public void setDsqThreadId(List<String> dsqThreadId) {
        this.dsqThreadId = dsqThreadId;
    }

    public CustomFields withDsqThreadId(List<String> dsqThreadId) {
        this.dsqThreadId = dsqThreadId;
        return this;
    }

}
