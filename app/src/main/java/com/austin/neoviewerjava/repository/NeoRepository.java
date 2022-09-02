package com.austin.neoviewerjava.repository;

import androidx.annotation.OptIn;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.database.NeoDao;
import com.austin.neoviewerjava.database.NeoDatabase;
import com.austin.neoviewerjava.network.NeoService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class NeoRepository {

    private final NeoService service;
    private final NeoDatabase db;
    private final NeoDao neoDao;

    @Inject
    public NeoRepository(NeoDatabase db, NeoService service) {
        this.service = service;
        this.db = db;
        this.neoDao = db.getNeoDao();
    }

    @OptIn(markerClass = androidx.paging.ExperimentalPagingApi.class)
    public Flowable<PagingData<Neo>> getPagingFlow() {
        // building the Pager
        Pager<Integer, Neo> pager = new Pager<Integer, Neo>(
                new PagingConfig(20, 120),
                NeoService.STARTING_PAGE,
                new NeoRemoteMediator(service, db),
                neoDao::getAll
        );

        return PagingRx.getFlowable(pager);
    }
}
