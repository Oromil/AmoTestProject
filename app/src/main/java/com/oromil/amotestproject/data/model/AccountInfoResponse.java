package com.oromil.amotestproject.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Created by Oromil on 28.07.2017.
 */

public class AccountInfoResponse {

    @Getter
    @SerializedName("response")
    @Expose
    private Response response;

    public class Response {
        @Getter
        @SerializedName("account")
        @Expose
        public Account account;
        @Getter
        @SerializedName("server_time")
        @Expose
        private Integer serverTime;

        public class Account {
            @Getter
            @SerializedName("leads_statuses")
            @Expose
            private List<LeadsStatus> leadsStatuses;
        }
    }
}
