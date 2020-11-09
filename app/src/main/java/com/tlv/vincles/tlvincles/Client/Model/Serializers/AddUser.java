package com.tlv.vincles.tlvincles.Client.Model.Serializers;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlv.vincles.tlvincles.Client.Model.GetUser;

//import cat.bcn.vincles.mobile.Client.Model.GetUser;


public class AddUser {

    @SerializedName("relationship")
    @Expose
    private String relationship;
    @SerializedName("userVincles")
    @Expose
    private GetUser userVincles;

    public String getRelationship() {
        return relationship;
    }

    public GetUser getUserVincles() {
        return userVincles;
    }
}
