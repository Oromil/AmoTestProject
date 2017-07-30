package com.oromil.amotestproject.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;

/**
 * Created by Oromil on 28.07.2017.
 */


public class LeadsStatus extends RealmObject{
    @Getter
    @SerializedName("id")
    @Expose
    private String id;
    @Getter
    @SerializedName("name")
    @Expose
    private String name;
    @Getter
    @SerializedName("pipeline_id")
    @Expose
    private Integer pipelineId;
    @Getter
    @SerializedName("sort")
    @Expose
    private String sort;
    @Getter
    @SerializedName("color")
    @Expose
    private String color;
    @Getter
    @SerializedName("editable")
    @Expose
    private String editable;
}
