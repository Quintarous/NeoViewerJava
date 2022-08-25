package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

public class NeoResponse {

    public final int id;
    public final String name;
    public final String designation;
    @SerializedName("nasa_jpl_url") public final String jplUrl;
    @SerializedName("is_potentially_hazardous_asteroid") public final boolean hazardous;
    @SerializedName("estimated_diameter") public final DiameterData diameterData;

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
