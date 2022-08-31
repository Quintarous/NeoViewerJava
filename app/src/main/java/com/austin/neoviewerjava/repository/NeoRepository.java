package com.austin.neoviewerjava.repository;

import static com.austin.neoviewerjava.Util.processNeoResponses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.database.NeoDao;
import com.austin.neoviewerjava.database.NeoDatabase;
import com.austin.neoviewerjava.network.BrowseResponse;
import com.austin.neoviewerjava.network.NeoResponse;
import com.austin.neoviewerjava.network.NeoService;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeoRepository {

    private final NeoService service;
    private final NeoDao dao;

    @Inject
    public NeoRepository(NeoDatabase db, NeoService service) {
        this.service = service;
        this.dao = db.getNeoDao();
    }

    private Single<BrowseResponse> getBrowseResponse() {
        return service.neoBrowse(0);
    }


}
