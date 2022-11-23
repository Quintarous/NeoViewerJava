package com.austin.neoviewerjava.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface FeedNeoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<FeedNeo> items);

    @Query("SELECT * FROM FeedNeoTable")
    Observable<List<FeedNeo>> getAll();

    @Query("DELETE FROM FeedNeoTable")
    void clear();
}
