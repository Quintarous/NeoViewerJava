package com.austin.neoviewerjava.repository;

import static com.austin.neoviewerjava.Util.processNeoResponses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.paging.LoadType;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxRemoteMediator;

import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.database.NeoDao;
import com.austin.neoviewerjava.database.NeoDatabase;
import com.austin.neoviewerjava.database.RemoteKeys;
import com.austin.neoviewerjava.database.RemoteKeysDao;
import com.austin.neoviewerjava.network.BrowseResponse;
import com.austin.neoviewerjava.network.NeoService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@OptIn(markerClass = androidx.paging.ExperimentalPagingApi.class)
public class NeoRemoteMediator extends RxRemoteMediator<Integer, Neo> {

    private final NeoService service;
    private final NeoDatabase db;
    private final RemoteKeysDao remoteKeysDao;
    private final NeoDao neoDao;

    @Inject
    public NeoRemoteMediator(NeoService service, NeoDatabase db) {
        this.service = service;
        this.db = db;
        remoteKeysDao = db.getRemoteKeysDao();
        neoDao = db.getNeoDao();
    }

    @NonNull
    @Override
    public Single<MediatorResult> loadSingle(@NonNull LoadType loadType,
                                             @NonNull PagingState<Integer, Neo> state) {
        // grabbing the appropriate RemoteKeys object based on the LoadType
        Single<RemoteKeys> rKey;
        switch (loadType) {
            case PREPEND:
                Neo first = state.firstItemOrNull();
                if (first == null) {
                    return Single.just(new MediatorResult.Success(false));
                }
                rKey = remoteKeysDao.getRemoteKeysByNeoId(first.id);
                break;

            case APPEND:
                Neo last = state.lastItemOrNull();
                if (last == null) {
                    return Single.just(new MediatorResult.Success(false));
                }
                rKey = remoteKeysDao.getRemoteKeysByNeoId(last.id);
                break;

            default: // REFRESH
                rKey = Single.just(new RemoteKeys(0, null, null));
                break;
        }

        return rKey
                .subscribeOn(Schedulers.io())
                .flatMap((Function<RemoteKeys, Single<MediatorResult>>) remoteKey -> {
                    // calculating which page to load
                    Integer page;
                    switch (loadType) {
                       case PREPEND:
                           page = remoteKey.prevKey;
                           break;

                       case APPEND:
                           page = remoteKey.nextKey;
                           break;

                       default: // REFRESH
                           page = NeoService.STARTING_PAGE;
                           // on a refresh load clear the database
                           db.runInTransaction(() -> {
                               neoDao.clearNeoTable();
                               remoteKeysDao.clearRemoteKeys();
                           });
                           break;
                    }

                    // if the page is null we have reached the end of our paginated data
                    if (page == null) {
                        return Single.just(new MediatorResult.Success(true));
                    }

                    // load data from the network
                    return service.neoBrowse(page)
                            .map(response -> {
                                // calculating a next and prev key based on the currently loaded page
                                boolean endOfPaginationReached = response.items.isEmpty();
                                Integer prevKey;
                                Integer nextKey;
                                if (page == NeoService.STARTING_PAGE) { prevKey = null; }
                                else { prevKey = page - 1; }
                                if (endOfPaginationReached) { nextKey = null; }
                                else { nextKey = page + 1; }

                                // processing the network response into nice Neo objects for the db
                                ArrayList<Neo> neoList = processNeoResponses(response.items);
                                // generating the new RemoteKeys objects to go with this new batch of Neo's
                                ArrayList<RemoteKeys> remoteKeysList = new ArrayList<RemoteKeys>();
                                neoList.forEach(neo ->
                                        remoteKeysList.add(new RemoteKeys(neo.id, prevKey, nextKey)));

                                // inserting the Neo's and RemoteKeys into the db
                                db.runInTransaction(() -> {
                                    neoDao.insertAll(neoList);
                                    remoteKeysDao.insertRemoteKeys(remoteKeysList);
                                });

                                return new MediatorResult.Success(endOfPaginationReached);
                            });
                });
    }
}
