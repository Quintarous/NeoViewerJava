package com.austin.neoviewerjava.network;

import com.google.gson.annotations.SerializedName;

public class PageStats {

    final int size;
    @SerializedName("total_elements") final int totalElements;
    @SerializedName("total_pages") final int totalPages;
    final int number;

    public PageStats(int size, int totalElements, int totalPages, int number) {
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }
}
