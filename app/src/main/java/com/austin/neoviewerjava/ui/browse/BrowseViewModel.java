package com.austin.neoviewerjava.ui.browse;

import android.util.Log;

import androidx.annotation.OptIn;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.network.NeoService;
import com.austin.neoviewerjava.repository.NeoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@HiltViewModel
public class BrowseViewModel extends ViewModel {

    private final NeoRepository repo;

    public final Flowable<PagingData<Neo>> dataFlow;

    @Inject
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    public BrowseViewModel(NeoRepository repo) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);

        this.repo = repo;
        dataFlow = PagingRx.cachedIn(repo.getPagingFlow(), viewModelScope);
    }
}