package com.austin.neoviewerjava.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NeoTable")
public class Neo {
    @PrimaryKey public final int id;
    public final String name;
    public final String designation;
    public final String jplUrl;
    public final boolean hazardous;
    public final Float kilometersMin;
    public final Float kilometersMax;
    public final Float metersMin;
    public final Float metersMax;
    public final Float milesMin;
    public final Float milesMax;
    public final Float feetMin;
    public final Float feetMax;

    public Neo(
            int id,
            String name,
            String designation,
            String jplUrl,
            boolean hazardous,
            Float kilometersMin,
            Float kilometersMax,
            Float metersMin,
            Float metersMax,
            Float milesMin,
            Float milesMax,
            Float feetMin,
            Float feetMax
    ) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.jplUrl = jplUrl;
        this.hazardous = hazardous;
        this.kilometersMin = kilometersMin;
        this.kilometersMax = kilometersMax;
        this.metersMin = metersMin;
        this.metersMax = metersMax;
        this.milesMin = milesMin;
        this.milesMax = milesMax;
        this.feetMin = feetMin;
        this.feetMax = feetMax;
    }
}
