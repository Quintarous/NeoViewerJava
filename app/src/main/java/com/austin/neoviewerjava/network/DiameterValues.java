package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

public class DiameterValues {
    @SerializedName("estimated_diameter_min") public final Float diameterMin;
    @SerializedName("estimated_diameter_max") public final Float diameterMax;

    public DiameterValues(Float diameterMin, Float diameterMax) {
        this.diameterMin = diameterMin;
        this.diameterMax = diameterMax;
    }
}
