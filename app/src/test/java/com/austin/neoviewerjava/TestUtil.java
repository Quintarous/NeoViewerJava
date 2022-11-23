package com.austin.neoviewerjava;

import com.austin.neoviewerjava.network.DiameterData;
import com.austin.neoviewerjava.network.DiameterValues;
import com.austin.neoviewerjava.network.FeedNeoResponse;
import com.austin.neoviewerjava.network.NeoResponse;

import java.util.ArrayList;

public class TestUtil {

    public static ArrayList<NeoResponse> getNeoResponseList() {
        // building two filler NeoResponse objects
        NeoResponse neoResponse1 = new NeoResponse(
                1, "name", "designation", "jplUrl", true,
                new DiameterData(
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F)
                )
        );
        NeoResponse neoResponse2 = new NeoResponse(
                2, "name", "designation", "jplUrl", false,
                new DiameterData(
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F)
                )
        );

        // creating an array with the filler NeoResponse objects
        final ArrayList<NeoResponse> responseList = new ArrayList<>();
        responseList.add(neoResponse1);
        responseList.add(neoResponse2);

        // returning the created list
        return responseList;
    }

    public static ArrayList<FeedNeoResponse> getFeedNeoResponseList() {
        // building two filler FeedNeoResponse objects
        FeedNeoResponse feedNeoResponse1 = new FeedNeoResponse(
                1, "name", "jplUrl", true,
                new DiameterData(
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F)
                )
        );
        FeedNeoResponse feedNeoResponse2 = new FeedNeoResponse(
                2, "name", "jplUrl", false,
                new DiameterData(
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F),
                        new DiameterValues(1F, 1F)
                )
        );

        // creating a list of FeedNeoResponse objects
        final ArrayList<FeedNeoResponse> feedResponseList = new ArrayList<>();
        feedResponseList.add(feedNeoResponse1);
        feedResponseList.add(feedNeoResponse2);

        // return the created list
        return feedResponseList;
    }
}
