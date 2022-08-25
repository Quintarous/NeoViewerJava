package com.austin.neoviewerjava.repository;

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

        // This is a test to check if the network and database function as expected (it passed!)
        final Call<BrowseResponse> call = getBrowseResponse();
        call.enqueue(new Callback<BrowseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BrowseResponse> call, @NonNull Response<BrowseResponse> response) {
                NeoDatabase.executor.execute(() -> {
                    Log.i("bruh", "response = " + response.body().items);
                    assert response.body() != null;
                    dao.insertAll(processNeoResponses(response.body().items));
                });
            }

            @Override
            public void onFailure(Call<BrowseResponse> call, Throwable t) {
            }
        });
    }

    private Call<BrowseResponse> getBrowseResponse() {
        return service.neoBrowse(0);
    }

    private ArrayList<Neo> processNeoResponses(ArrayList<NeoResponse> list) {
        final ArrayList<Neo> outputList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            NeoResponse response = list.get(i);
            outputList.add(new Neo(
                    response.id,
                    response.name,
                    response.designation,
                    response.jplUrl,
                    response.hazardous,
                    response.diameterData.kilometers.diameterMin,
                    response.diameterData.kilometers.diameterMax,
                    response.diameterData.meters.diameterMin,
                    response.diameterData.meters.diameterMax,
                    response.diameterData.miles.diameterMin,
                    response.diameterData.miles.diameterMax,
                    response.diameterData.feet.diameterMin,
                    response.diameterData.feet.diameterMax
            ));
        }
        return outputList;
    }
}
