package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class FeedResponse {
    @SerializedName("near_earth_objects") public final Map<String, ArrayList<FeedNeoResponse>> days;

    FeedResponse(Map<String, ArrayList<FeedNeoResponse>> days) {
        this.days = days;
    }
}
