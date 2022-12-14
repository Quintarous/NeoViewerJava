package com.austin.neoviewerjava.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Neo.class, RemoteKeys.class, FeedNeo.class}, version = 3, exportSchema = false)
public abstract class NeoDatabase extends RoomDatabase {

    public abstract NeoDao getNeoDao();
    public abstract FeedNeoDao getFeedNeoDao();
    public abstract RemoteKeysDao getRemoteKeysDao();
    public static final ExecutorService executor = Executors.newCachedThreadPool();
}
