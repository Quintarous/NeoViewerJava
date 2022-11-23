package com.austin.neoviewerjava.ui.feed;

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

    private final Observer<FeedData> feedDataObserver = feedData -> {
        if (feedData.getClass() == FeedData.Success.class) {
            _state.setValue(new UiState.Success(((FeedData.Success) feedData).feedNeoList));
        } else if (feedData.getClass() == FeedData.Error.class) {
            _state.setValue(new UiState.Error(((FeedData.Error) feedData).e));
        }
    };


    @Inject
    public FeedViewModel(NeoRepository repo) {
        this.repo = repo;
        // observe
        repo.feedData.observeForever(feedDataObserver);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        // the observer must be manually removed since we are using the observeForever() method
        repo.feedData.removeObserver(feedDataObserver);
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
