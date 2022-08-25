package com.austin.neoviewerjava.database;

import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NeoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<Neo> neoList);

    @Query("SELECT * FROM NeoTable")
    PagingSource<Integer, Neo> getAll();

    @Query("DELETE FROM NeoTable")
    void clearNeoTable();
}
