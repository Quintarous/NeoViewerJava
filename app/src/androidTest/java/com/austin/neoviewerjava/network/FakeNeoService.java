package com.austin.neoviewerjava.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.rxjava3.core.Single;

public class FakeNeoService implements NeoService {

    BrowseResponse browseResponse;

    @Override
    public Single<BrowseResponse> neoBrowse(int page) {
        if (browseResponse == null) {
            return Single.error(new IOException());
        }
        return Single.just(browseResponse);
    }

    // these methods configure what the fake service returns from it's neoBrowse() method
    public void throwException() {
        browseResponse = null;
    }
    public void returnWithData() {
        browseResponse = populatedBrowseResponse;
    }
    public void returnWithoutData() {
        browseResponse = emptyBrowseResponse;
    }

    private final NeoResponse neoResponse1 = new NeoResponse(
            1,
            "neo1",
            "designation",
            "jplUrl",
            false,
            new DiameterData(
                    new DiameterValues(1F, 1F),
                    new DiameterValues(1F, 1F),
                    new DiameterValues(1F, 1F),
                    new DiameterValues(1F, 1F)
            )
    );
    private final NeoResponse neoResponse2 = new NeoResponse(
            2,
            "neo2",
            "designation",
            "jplUrl",
            false,
            new DiameterData(
                    new DiameterValues(1F, 1F),
                    new DiameterValues(1F, 1F),
                    new DiameterValues(1F, 1F),
                    new DiameterValues(1F, 1F)
            )
    );

    private final BrowseResponse emptyBrowseResponse = new BrowseResponse(
            new PageStats(
                    20,
                    29364,
                    1469,
                    0
            ),
            new ArrayList<NeoResponse>()
    );
    private final BrowseResponse populatedBrowseResponse = new BrowseResponse(
            new PageStats(
                    20,
                    29364,
                    1469,
                    0
            ),
            new ArrayList<>(Arrays.asList(neoResponse1, neoResponse2))
    );
}
