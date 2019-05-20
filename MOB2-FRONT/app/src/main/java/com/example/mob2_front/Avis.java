package com.example.mob2_front;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avis {
    @SerializedName("avis")
    @Expose
    private String avis;

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }
}
