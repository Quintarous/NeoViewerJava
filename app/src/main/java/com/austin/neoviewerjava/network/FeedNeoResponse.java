package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

public class FeedNeoResponse {
    public final Integer id;
    public final String name;
    @SerializedName("nasa_jpl_url") public final String jplUrl;
    @SerializedName("is_potentially_hazardous_asteroid") public final Boolean hazardous;
    @SerializedName("estimated_diameter") public final DiameterData diameterData;

    public FeedNeoResponse(Integer id, String name, String jplUrl, Boolean hazardous, DiameterData diameterData) {
        this.id = id;
        this.name = name;
        this.jplUrl = jplUrl;
        this.hazardous = hazardous;
        this.diameterData = diameterData;
    }
}