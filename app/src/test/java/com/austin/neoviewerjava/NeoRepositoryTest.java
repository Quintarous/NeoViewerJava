package com.austin.neoviewerjava;

import static com.austin.neoviewerjava.TestUtil.getFeedNeoResponseList;
import static com.austin.neoviewerjava.Util.processFeedData;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.austin.neoviewerjava.database.FeedNeo;
import com.austin.neoviewerjava.database.FeedNeoDao;
import com.austin.neoviewerjava.database.NeoDao;
import com.austin.neoviewerjava.database.NeoDatabase;
import com.austin.neoviewerjava.network.FakeNeoService;
import com.austin.neoviewerjava.network.NeoService;
import com.austin.neoviewerjava.repository.NeoRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

public class NeoRepositoryTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();


    // instantiating our fake service and initializing our mock database
    private final NeoService service = new FakeNeoService();
    private final NeoDao mockNeoDao;
    private final FeedNeoDao mockFeedNeoDao;
    private final NeoDatabase mockDatabase;

    public NeoRepositoryTest() {
        // creating mocks for both DAO's
        this.mockNeoDao = mock(NeoDao.class);
        this.mockFeedNeoDao = mock(FeedNeoDao.class);
        when(mockFeedNeoDao.getAll()).thenReturn(Observable.empty());

        // creating and stubbing our database mock
        this.mockDatabase = mock(NeoDatabase.class);
        when(mockDatabase.getNeoDao()).thenReturn(mockNeoDao);
        when(mockDatabase.getFeedNeoDao()).thenReturn(mockFeedNeoDao);
    }

    @Before
    public void setup() {
        // setting the service back to it's default state
        FakeNeoService fakeService = (FakeNeoService) service;
        fakeService.returnErrorBrowseResponse();
        fakeService.returnErrorFeedResponse();
    }

    @Test
    public void cacheFeedData_givenData_InsertsIntoDatabase() {
        // telling the service to return good data
        FakeNeoService fakeService = (FakeNeoService) service;
        fakeService.returnPopulatedFeedResponse();

        // class under test
        final NeoRepository repo = new NeoRepository(mockDatabase, service);
        // making a network request
        repo.requestNewFeedData("start date", "end date");

        // getting the expected list
        final ArrayList<FeedNeo> expected =
                new ArrayList<>(processFeedData("2015-09-08", getFeedNeoResponseList()));

        // verifying the repo inserted the expected list into the database
        verify(mockFeedNeoDao).insert(expected);
    }

    // TODO write the rest of the repository tests
}
