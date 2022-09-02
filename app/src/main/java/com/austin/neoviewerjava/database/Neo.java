package com.austin.neoviewerjava.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NeoTable")
public class Neo {
    @PrimaryKey public final int id;
    public final String name;
    public final String designation;
    public final String jplUrl;
    public final Boolean hazardous;
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


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Neo neo = (Neo) obj;

        return (this.id == neo.id && this.name.equals(neo.name) && this.hazardous == neo.hazardous
        && this.jplUrl.equals(neo.jplUrl) && this.designation.equals(neo.designation)
        && this.kilometersMin.equals(neo.kilometersMin) && this.kilometersMax.equals(neo.kilometersMax)
        && this.metersMin.equals(neo.metersMin) && this.metersMax.equals(neo.metersMax)
        && this.milesMin.equals(neo.milesMin) && this.milesMax.equals(neo.milesMax)
        && this.feetMin.equals(neo.feetMin) && this.feetMax.equals(neo.feetMax));
    }


    @Override
    public int hashCode() {
        return this.id;
    }
}
