package com.austin.neoviewerjava;

import androidx.paging.LoadType;
import androidx.paging.PagingConfig;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import androidx.paging.RemoteMediator;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.MediumTest;

import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.database.NeoDatabase;
import com.austin.neoviewerjava.network.InstrumentedFakeNeoService;
import com.austin.neoviewerjava.repository.NeoRemoteMediator;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;

@MediumTest
public class NeoRemoteMediatorTest {

    InstrumentedFakeNeoService fakeService = new InstrumentedFakeNeoService();
    NeoDatabase db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), NeoDatabase.class)
            .fallbackToDestructiveMigration()
            .build();

    Disposable disposable;


    @After
    public void cleanup() { // resetting our fake service and in memory database to their initial states
        fakeService.throwException();
        db.clearAllTables();
        // if the disposable hasn't been disposed then dispose of it and nullify the variable
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }


    @Test
    public void refreshLoadReturnsSuccessWhenDataIsPresent() throws InterruptedException {
        // telling the fake service to return good data
        fakeService.returnWithData();
        NeoRemoteMediator remoteMediator = new NeoRemoteMediator(fakeService, db);

        // perform a refresh load
        Single<RemoteMediator.MediatorResult> resultSingle = remoteMediator.loadSingle(
                LoadType.REFRESH,
                new PagingState<>(
                        new ArrayList<PagingSource.LoadResult.Page<Integer, Neo>>(),
                        null,
                        new PagingConfig(20),
                        20
                )
        );

        // subscribe a test observer to our single
        TestObserver<RemoteMediator.MediatorResult> observer = resultSingle.test();
        disposable = observer; // cache it so it can be disposed of later

        // wait for the single to emit and make our assertions on the output
        observer.await();
        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValue(mediatorResult -> {
            // if it is a MediatorResult.Success object
            if (mediatorResult instanceof RemoteMediator.MediatorResult.Success) {
                // AND the endOfPaginationReached value is false return true otherwise return false
                RemoteMediator.MediatorResult.Success success =
                        (RemoteMediator.MediatorResult.Success) mediatorResult;
                return !success.endOfPaginationReached();
            }
            return false;
        });
    }


    @Test
    public void refreshLoadReturnsEndOfPaginationWhenDataIsMissing() throws InterruptedException {
        fakeService.returnWithoutData(); // telling the fake network to return an empty list

        NeoRemoteMediator remoteMediator = new NeoRemoteMediator(fakeService, db); // class under test
        // requesting data from the remote mediator
        Single<RemoteMediator.MediatorResult> singleResult = remoteMediator.loadSingle(
                LoadType.REFRESH,
                new PagingState<>(
                        new ArrayList<PagingSource.LoadResult.Page<Integer, Neo>>(),
                        null,
                        new PagingConfig(20),
                        20
                )
        );

        // subscribing to the singleResult
        TestObserver<RemoteMediator.MediatorResult> observer = singleResult.test();
        disposable = observer; // caching the test observer so it can be disposed of later

        // wait for the singleResult to emit then make our assertions
        observer.await();
        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValue(mediatorResult -> {
            if (mediatorResult instanceof RemoteMediator.MediatorResult.Success) {
                // AND the endOfPaginationReached value is false return true otherwise return false
                RemoteMediator.MediatorResult.Success success =
                        (RemoteMediator.MediatorResult.Success) mediatorResult;
                return success.endOfPaginationReached();
            }
            return false;
        });
    }


    @Test
    public void refreshLoadReturnsErrorWhenExceptionIsThrown() throws InterruptedException {
        // the fake service throws an exception by default so no need to set that here
        NeoRemoteMediator remoteMediator = new NeoRemoteMediator(fakeService, db); // class under test
        // requesting data from the remote mediator
        Single<RemoteMediator.MediatorResult> singleResult = remoteMediator.loadSingle(
                LoadType.REFRESH,
                new PagingState<>(
                        new ArrayList<PagingSource.LoadResult.Page<Integer, Neo>>(),
                        null,
                        new PagingConfig(20),
                        20
                )
        );

        // subscribing to the singleResult
        TestObserver<RemoteMediator.MediatorResult> observer = singleResult.test();
        disposable = observer; // caching the test observer so it can be disposed of later

        // wait for the singleResult to emit then make our assertions
        observer.await();
        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValue(mediatorResult ->
                mediatorResult instanceof RemoteMediator.MediatorResult.Error);
    }
}
