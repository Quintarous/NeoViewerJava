package com.austin.neoviewerjava.ui.browse;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.austin.neoviewerjava.network.NeoService;
import com.austin.neoviewerjava.repository.NeoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@HiltViewModel
public class BrowseViewModel extends ViewModel {

    private final NeoRepository repo;

    private final MutableLiveData<String> mText;

    @Inject
    public BrowseViewModel(NeoRepository repo) {
        this.repo = repo;
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        Log.i("bruh", "repo = " + this.repo);
    }

    public LiveData<String> getText() {
        return mText;
    }
}