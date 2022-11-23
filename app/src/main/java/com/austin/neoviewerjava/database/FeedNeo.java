package com.austin.neoviewerjava.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FeedNeoTable")
public class FeedNeo {
    @PrimaryKey
    public final int id;
    public final String name;
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
    public final String date;

    public FeedNeo(
            int id,
            String name,
            String jplUrl,
            boolean hazardous,
            Float kilometersMin,
            Float kilometersMax,
            Float metersMin,
            Float metersMax,
            Float milesMin,
            Float milesMax,
            Float feetMin,
            Float feetMax,
            String date) {
        this.id = id;
        this.name = name;
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
        this.date = date;
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        FeedNeo feedNeo = (FeedNeo) obj;

        return (this.id == feedNeo.id && this.name.equals(feedNeo.name) && this.hazardous == feedNeo.hazardous
                && this.jplUrl.equals(feedNeo.jplUrl) && this.date.equals(feedNeo.date)
                && this.kilometersMin.equals(feedNeo.kilometersMin) && this.kilometersMax.equals(feedNeo.kilometersMax)
                && this.metersMin.equals(feedNeo.metersMin) && this.metersMax.equals(feedNeo.metersMax)
                && this.milesMin.equals(feedNeo.milesMin) && this.milesMax.equals(feedNeo.milesMax)
                && this.feetMin.equals(feedNeo.feetMin) && this.feetMax.equals(feedNeo.feetMax));
    }


    @Override
    public int hashCode() {
        return this.id;
    }
}
