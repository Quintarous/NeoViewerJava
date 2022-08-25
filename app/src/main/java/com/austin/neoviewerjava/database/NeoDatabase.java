package com.austin.neoviewerjava.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.jvm.Volatile;

@Database(entities = Neo.class, version = 1, exportSchema = false)
public abstract class NeoDatabase extends RoomDatabase {

    public abstract NeoDao getNeoDao();
    public static final ExecutorService executor = Executors.newCachedThreadPool();
}
