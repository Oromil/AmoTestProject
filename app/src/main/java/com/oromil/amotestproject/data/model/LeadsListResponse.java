package com.oromil.amotestproject.data.model;

/**
 * Created by Oromil on 26.07.2017.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;


public class LeadsListResponse {

    @Getter
    @SerializedName("response")
    @Expose
    private Response response;

    public class Response {

        @Getter
        @SerializedName("leads")
        @Expose
        private List<Lead> leads;
        @Getter
        @SerializedName("server_time")
        @Expose
        private Integer serverTime;
        }
}
