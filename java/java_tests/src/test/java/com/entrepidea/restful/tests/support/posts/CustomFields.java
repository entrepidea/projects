
package com.entrepidea.restful.tests.support.posts;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
