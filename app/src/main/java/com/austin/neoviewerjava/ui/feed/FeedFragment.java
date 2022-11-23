package com.austin.neoviewerjava.ui.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.austin.neoviewerjava.databinding.FragmentFeedBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FeedFragment extends Fragment {

    private FragmentFeedBinding binding;
    private FeedViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        binding = FragmentFeedBinding.inflate(inflater, container, false);

        viewModel.state.observe(this.getViewLifecycleOwner(), state -> {
            // TODO build the UI to display the state
            Log.i("bruh", state.toString());
        });

        return binding.getRoot();
    }
}
