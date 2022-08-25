package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

public class DiameterData {

    public final DiameterValues kilometers;
    public final DiameterValues meters;
    public final DiameterValues miles;
    public final DiameterValues feet;

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
