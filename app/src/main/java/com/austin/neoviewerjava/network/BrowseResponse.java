package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BrowseResponse {

    @SerializedName("page") public final PageStats pageStats;
    @SerializedName("near_earth_objects") public final ArrayList<NeoResponse> items;

    public BrowseResponse(PageStats pageStats, ArrayList<NeoResponse> items){
        this.pageStats = pageStats;
        this.items = items;
    }
}
