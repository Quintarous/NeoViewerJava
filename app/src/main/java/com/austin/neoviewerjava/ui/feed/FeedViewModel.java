package com.austin.neoviewerjava.ui.feed;

import android.icu.util.Calendar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.austin.neoviewerjava.database.FeedNeo;
import com.austin.neoviewerjava.repository.FeedData;
import com.austin.neoviewerjava.repository.NeoRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FeedViewModel extends ViewModel {

    private final NeoRepository repo;
    private final MutableLiveData<UiState> _state = new MutableLiveData<>();
    public final LiveData<UiState> state = _state;

    // observing the data from the repository
    private final Observer<FeedData> feedDataObserver = feedData -> {
        // if it's a successful return emit a success UI state
        if (feedData.getClass() == FeedData.Success.class) {
            _state.setValue(new UiState.Success(((FeedData.Success) feedData).feedNeoList));
        }
        // if it's an error return emit an error UI state
        else if (feedData.getClass() == FeedData.Error.class) {
            _state.setValue(new UiState.Error(((FeedData.Error) feedData).e));
        }
    };


    @Inject
    public FeedViewModel(NeoRepository repo) {
        this.repo = repo;
        // observing the feed data exposed from the repo
        repo.feedData.observeForever(feedDataObserver);
    }


    /**
     * Takes a start and end date as unix times and uses them to make a make a network request for
     * the data.
     * @param start the start day for the request
     * @param end the end day for the request
     */
    public void requestNewFeedData(@NonNull Long start, @NonNull Long end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(start);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(end);

        String startDate = formatDateForApi(startCalendar);
        String endDate = formatDateForApi(endCalendar);
        repo.requestNewFeedData(startDate, endDate);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        // the observer must be manually removed since we are using the observeForever() method
        repo.feedData.removeObserver(feedDataObserver);
    }


    /**
     * The NeoWs Nasa API requires the start and end date in a yyyy-mm-dd format. This function
     * takes a Calendar object and generates an appropriate string from it.
     */
    @NonNull private String formatDateForApi(@NonNull Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }
}


/**
 * UiState is a wrapper class for two subclasses of actual state that is passed down to the UI.
 * Success, and Error.
 * Success contains a list of the retrieved data (FeedNeo).
 * Error contains a Throwable, likely a network error but the specifics are not
 * important as far as the UI is concerned.
 */
class UiState {
    static class Success extends UiState {
        public final List<FeedNeo> feedNeoList;
        public Success(List<FeedNeo> feedNeoList) {
            this.feedNeoList = feedNeoList;
        }
    }

    static class Error extends UiState {
        public final Throwable e;
        public Error(Throwable e) {
            this.e = e;
        }
    }
}
