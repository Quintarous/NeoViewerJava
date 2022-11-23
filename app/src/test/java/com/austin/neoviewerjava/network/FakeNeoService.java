package com.austin.neoviewerjava.network;

import static com.austin.neoviewerjava.TestUtil.getFeedNeoResponseList;
import static com.austin.neoviewerjava.TestUtil.getNeoResponseList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class FakeNeoService implements NeoService {

    // browseResponse determines what will be returned by neoBrowse()
    private BrowseResponse browseResponse = null;
    private final BrowseResponse populatedBrowseResponse;
    private final BrowseResponse emptyBrowseResponse;

    // feedResponse determines what will be returned by neoFeed()
    private FeedResponse feedResponse = null;
    private final FeedResponse populatedFeedResponse;
    private final FeedResponse emptyFeedResponse;


    public FakeNeoService() {
        // instantiating the populated and empty BrowseResponse objects
        this.populatedBrowseResponse = new BrowseResponse(
                new PageStats(2, 2, 1, 1),
                getNeoResponseList()
        );
        this.emptyBrowseResponse = new BrowseResponse(
                new PageStats(0, 0, 0, 1),
                new ArrayList<>()
        );

        // creating a map to construct our FeedResponse with
        final Map<String, ArrayList<FeedNeoResponse>> days = new LinkedHashMap<>();
        days.put("2015-09-08", getFeedNeoResponseList());

        // creating the FeedResponses that will determine the neoFeed() method's return type
        this.populatedFeedResponse = new FeedResponse(days);
        this.emptyFeedResponse = new FeedResponse(new LinkedHashMap<>());
    }


    // methods to change the behavior(return type) of the neoBrowse() method
    public void returnErrorBrowseResponse() { browseResponse = null; }
    public void returnPopulatedBrowseResponse() { browseResponse = populatedBrowseResponse; }
    public void returnEmptyBrowseResponse() { browseResponse = emptyBrowseResponse; }

    // methods to change the behavior(return type) of the neoFeed() method
    public void returnErrorFeedResponse() { feedResponse = null; }
    public void returnPopulatedFeedResponse() { feedResponse = populatedFeedResponse; }
    public void returnEmptyFeedResponse() { feedResponse = emptyFeedResponse; }


    @Override
    public Single<BrowseResponse> neoBrowse(int page) {
        if (browseResponse == null) {
            return Single.error(new Throwable());
        } else {
            return Single.just(browseResponse);
        }
    }

    @Override
    public Single<FeedResponse> neoFeed(String startDate, String endDate) {
        if (feedResponse == null) {
            return Single.error(new Throwable());
        } else {
            return Single.just(feedResponse);
        }
    }
}
