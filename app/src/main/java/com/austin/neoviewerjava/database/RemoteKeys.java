package com.austin.neoviewerjava.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RemoteKeys {

    @PrimaryKey
    public final int neoId;
    public final Integer prevKey;
    public final Integer nextKey;

    public RemoteKeys(Integer neoId, Integer prevKey, Integer nextKey) {
        this.neoId = neoId;
        this.prevKey = prevKey;
        this.nextKey = nextKey;
    }
}
