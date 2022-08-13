package com.austin.neoviewerjava.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NeoDao {

    @Insert
    void insertAll(Neo... neoList);

    @Query("SELECT * FROM NeoTable")
    List<Neo> getAll();

    @Query("DELETE FROM NeoTable")
    void clearNeoTable();
}
