package com.austin.neoviewerjava.network;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NeoService {

    String BASE_URL = "https://api.nasa.gov/neo/rest/v1/";
    String API_KEY = "TZIMsTB3ztsDc6fdqEyTqGtNy7Dr7Goqe5L1xvvC";
    int STARTING_PAGE = 0;

    @GET("neo/browse?api_key=" + API_KEY)
    Single<BrowseResponse> neoBrowse(@Query("page") int page);

    @GET("feed?api_key=" + API_KEY)
    Single<FeedResponse> neoFeed(
            @Query("start_date") String startDate,
            @Query("end_date") String endDate
    );
}
