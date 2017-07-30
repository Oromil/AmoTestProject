package com.oromil.amotestproject.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import lombok.Getter;

/**
 * Created by Oromil on 27.07.2017.
 */


public class Lead extends RealmObject{
    @Getter
    @SerializedName("id")
    @Expose
    private String id;
    @Getter
    @SerializedName("name")
    @Expose
    private String name;
    @Getter
    @SerializedName("last_modified")
    @Expose
    private Integer lastModified;
    @Getter
    @SerializedName("status_id")
    @Expose
    private String statusId;
    @Getter
    @SerializedName("price")
    @Expose
    private String price;
    @Getter
    @SerializedName("responsible_user_id")
    @Expose
    private String responsibleUserId;
    @Getter
    @SerializedName("date_create")
    @Expose
    private Integer dateCreate;
    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("created_user_id")
    @Expose
    private String createdUserId;
    @SerializedName("date_close")
    @Expose
    private Integer dateClose;

}
