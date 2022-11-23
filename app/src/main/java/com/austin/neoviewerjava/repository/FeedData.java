package com.austin.neoviewerjava.repository;

import com.austin.neoviewerjava.database.FeedNeo;

import java.util.List;

/**
 * FeedData is a wrapper class for data being exposed to consumers (like the [FeedViewModel]).
 * The Success subclass contains the list of FeedNeo objects retrieved from the db.
 * The Error subclass contains a Throwable error from either the network or db.
 */
public class FeedData {
    public static class Success extends FeedData {
        public final List<FeedNeo> feedNeoList;
        public Success(List<FeedNeo> feedNeoList) {
            this.feedNeoList = feedNeoList;
        }
    }

    public static class Error extends FeedData {
        public final Throwable e;
        public Error(Throwable e) {
            this.e = e;
        }
    }
}
