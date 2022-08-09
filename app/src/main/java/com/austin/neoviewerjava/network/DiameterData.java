package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

public class DiameterData {

    final DiameterValues kilometers;
    final DiameterValues meters;
    final DiameterValues miles;
    final DiameterValues feet;

    public DiameterData(
            DiameterValues kilometers,
            DiameterValues meters,
            DiameterValues miles,
            DiameterValues feet
    ) {
        this.kilometers = kilometers;
        this.meters = meters;
        this.miles = miles;
        this.feet = feet;
    }
}

class DiameterValues {
    @SerializedName("estimated_diameter_min") final Float diameterMin;
    @SerializedName("estimated_diameter_max") final Float diameterMax;

    public DiameterValues(Float diameterMin, Float diameterMax) {
        this.diameterMin = diameterMin;
        this.diameterMax = diameterMax;
    }
}
