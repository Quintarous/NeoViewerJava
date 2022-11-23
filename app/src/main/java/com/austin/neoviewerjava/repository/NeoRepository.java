package com.austin.neoviewerjava.repository;

import static com.austin.neoviewerjava.Util.processFeedData;

import androidx.annotation.OptIn;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.austin.neoviewerjava.database.FeedNeo;
import com.austin.neoviewerjava.database.FeedNeoDao;
import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.database.NeoDao;
import com.austin.neoviewerjava.database.NeoDatabase;
import com.austin.neoviewerjava.network.FeedNeoResponse;
import com.austin.neoviewerjava.network.FeedResponse;
import com.austin.neoviewerjava.network.NeoService;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class NeoRepository {

    private final NeoService service;
    private final NeoDatabase db;
    private final NeoDao neoDao;
    private final FeedNeoDao feedNeoDao;

    @Inject
    public NeoRepository(NeoDatabase db, NeoService service) {
        this.service = service;
        this.db = db;
        this.neoDao = db.getNeoDao();
        this.feedNeoDao = db.getFeedNeoDao();

        // subscribing to the db and emitting the results to the ViewModel
        feedNeoDao.getAll().subscribe(
                feedNeoList -> _feedData.postValue(new FeedData.Success(feedNeoList)),
                throwable -> _feedData.postValue(new FeedData.Error(throwable))
        );
    }

    /**
     * @return Flowable<PagingData<Neo>> Returns a RxFlowable of paging data to be consumed by the
     * BrowseFragmentViewModel.
     */
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


    // exposing a request in progress live data to avoid triggering overlapping network requests
    private final MutableLiveData<Boolean> _requestInProgress = new MutableLiveData<>(false);
    public final LiveData<Boolean> requestInProgress = _requestInProgress;
    // this disposable is used to cancel an in progress network request
    private Disposable networkDisposable;

    // TODO viewmodel shouldn't observe livedata so find a way to do this with observables
    // exposing the database data as well as any network errors in a single livedata for the ViewModel
    private final MutableLiveData<FeedData> _feedData = new MutableLiveData<>();
    public final LiveData<FeedData> feedData = _feedData;


    /**
     * Makes a network request for feed data given the start and end date. Then caches the network
     * response in the database.
     * @param startDate the start date for the request (YYYY-MM-DD)
     * @param endDate the end date for the request (YYYY-MM-DD)
     */
    private void cacheFeedData(String startDate, String endDate) {
        // indicate a network request is in progress
        _requestInProgress.setValue(true);

        // get the network response RxJava single
        final Single<FeedResponse> responseSingle = service.neoFeed(startDate, endDate);
        // subscribing to the single
        responseSingle.subscribe(new SingleObserver<FeedResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                networkDisposable = d;
            }
            @Override
            public void onSuccess(@NonNull FeedResponse feedResponse) {
                // initialize the final list we'll insert into the db
                ArrayList<FeedNeo> processedFeedNeos = new ArrayList<>();
                // process the network response into FeedNeo objects and add them to the final list
                feedResponse.days.forEach((String day, ArrayList<FeedNeoResponse> neos) -> {
                    processedFeedNeos.addAll(processFeedData(day, neos));
                });
                // clear the db before we insert the new data into it
                feedNeoDao.clear();
                // insert the final list into the db
                feedNeoDao.insert(processedFeedNeos);
                _requestInProgress.postValue(false);
                networkDisposable = null;
            }
            @Override
            public void onError(@NonNull Throwable e) {
                _requestInProgress.postValue(false);
                // nullify the network call disposable since it's now completed with an error
                networkDisposable = null;
                // emit on the feedData live data to notify the ViewModel of this error
                _feedData.postValue(new FeedData.Error(e));
            }
        });
    }

    /**
     * Cancels the previous network request if there is currently one in progress then starts a new
     * network request.
     * @param startDate the start date for the request (YYYY-MM-DD)
     * @param endDate the end date for the request (YYYY-MM-DD)
     */
    public void requestNewFeedData(String startDate, String endDate) {
        if (Boolean.TRUE.equals(requestInProgress.getValue())) {
            if (networkDisposable != null) {
                if (!networkDisposable.isDisposed()) {
                    // if the disposable has not been disposed then dispose and nullify it
                    networkDisposable.dispose();
                    networkDisposable = null;
                }
            }
        }
        // now we can safely make a new network request
        cacheFeedData(startDate, endDate);
    }
}
