package com.austin.neoviewerjava.repository;

import android.content.Context;
import android.util.Log;

import com.austin.neoviewerjava.Util;
import com.austin.neoviewerjava.database.NeoDao;
import com.austin.neoviewerjava.database.NeoDatabase;
import com.austin.neoviewerjava.network.BrowseResponse;
import com.austin.neoviewerjava.network.NeoService;

import retrofit2.Call;

public class NeoRepository {

    private final Context context;
    private final NeoService service;
    private final NeoDao dao;

    public NeoRepository(Context context, NeoService service) {
        this.context = context;
        this.service = service;
        final NeoDatabase db = NeoDatabase.getInstance(context);
        this.dao = db.getNeoDao();

        // TODO setup a test
    }

    private Call<BrowseResponse> getBrowseResponse() {
        return service.neoBrowse(0);
    }
}
