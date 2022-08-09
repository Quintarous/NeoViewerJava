package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

public class NeoResponse {

    final int id;
    final String name;
    final String designation;
    @SerializedName("nasa_jpl_url") final String jplUrl;
    @SerializedName("is_potentially_hazardous_asteroid") final boolean hazardous;
    @SerializedName("estimated_diameter") final DiameterData diameterData;

    public NeoResponse(
            int id,
            String name,
            String designation,
            String jplUrl,
            boolean hazardous,
            DiameterData diameterData
    ) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.jplUrl = jplUrl;
        this.hazardous = hazardous;
        this.diameterData = diameterData;
    }
}
