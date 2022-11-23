package com.austin.neoviewerjava;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.austin.neoviewerjava.network.DiameterData;
import com.austin.neoviewerjava.network.DiameterValues;
import com.austin.neoviewerjava.network.FeedNeoResponse;
import com.austin.neoviewerjava.network.NeoResponse;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
public class TestUtil {

    /**
     * Waits for the given amount of time for the livedata to emit a value and then returns that
     * value. If no value was emitted by the livedata then this method will throw an
     * InterruptedException.
     *
     * This is done by setting a countdown latch for the given time and observing the livedata
     * forever. If the observer is triggered by the livedata emitting a value then the latch is
     * counted down and the value is returned.
     *
     * @param liveData The livedata to be observed
     * @param <T> The type of data the livedata emits
     * @return Returns T
     * @throws InterruptedException A simple runtime exception
     */
    public static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw new RuntimeException("LiveData value was never set.");
        }
        //noinspection unchecked
        return (T) data[0];
    }


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
