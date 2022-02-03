package net.airith.medicspot3;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Hospital {

    @SerializedName("hospID")
    @Expose
    public String hospID;
    @SerializedName("hospName")
    @Expose
    public String hospName;
    @SerializedName("states")
    @Expose
    public String states;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("log")
    @Expose
    public String log;

}