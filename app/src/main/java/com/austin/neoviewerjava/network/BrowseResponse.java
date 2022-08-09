package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseResponse {

    @SerializedName("page") final PageStats pageStats;
    @SerializedName("near_earth_objects") final List<NeoResponse> items;

    public BrowseResponse(PageStats pageStats, List<NeoResponse> items){
        this.pageStats = pageStats;
        this.items = items;
    }
}
