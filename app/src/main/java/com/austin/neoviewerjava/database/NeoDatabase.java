package com.austin.neoviewerjava.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kotlin.jvm.Volatile;

@Database(entities = Neo.class, version = 1, exportSchema = false)
public abstract class NeoDatabase extends RoomDatabase {

    public abstract NeoDao getNeoDao();

    @Volatile
    private static NeoDatabase INSTANCE;

    public static NeoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(
                    context,
                    NeoDatabase.class
            ).build();
        }
        return INSTANCE;
    }
}
