package com.austin.neoviewerjava.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRemoteKeys(ArrayList<RemoteKeys> list);

    @Query("SELECT * FROM RemoteKeys WHERE neoId = :id")
    Single<RemoteKeys> getRemoteKeysByNeoId(int id);

    @Query("DELETE FROM RemoteKeys")
    void clearRemoteKeys();
}
