package com.austin.neoviewerjava.network;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NeoService {

    String BASE_URL = "https://api.nasa.gov/neo/rest/v1/";
    String API_KEY = "TZIMsTB3ztsDc6fdqEyTqGtNy7Dr7Goqe5L1xvvC";
    int STARTING_PAGE = 0;

    @GET("neo/browse?api_key=TZIMsTB3ztsDc6fdqEyTqGtNy7Dr7Goqe5L1xvvC")
    BrowseResponse neoBrowse(@Query("page") int page);
}
