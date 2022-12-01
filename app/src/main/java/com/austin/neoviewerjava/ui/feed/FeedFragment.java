package com.austin.neoviewerjava.ui.feed;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.austin.neoviewerjava.R;
import com.austin.neoviewerjava.databinding.FragmentFeedBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FeedFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // putting the context in a variable so we don't have to call this method a million times
        Context context = requireContext();
        // instantiating the ViewModel
        FeedViewModel viewModel =
                new ViewModelProvider(this).get(FeedViewModel.class);
        // instantiating the view binding
        FragmentFeedBinding binding =
                FragmentFeedBinding.inflate(inflater, container, false);


        binding.selectDateRangeLabel.setOnClickListener((v -> {
            // TODO this is just to test
            viewModel.requestNewFeedData(1669674601L, 1669876201L);
            Toast.makeText(
                    context,
                    "test request made",
                    Toast.LENGTH_SHORT
            ).show();
        }));


        // getting an instance of the ClipboardManager
        ClipboardManager clipboard = context.getSystemService(ClipboardManager.class);
        // instantiating the adapter for our recycler view with it's copy lambda
        FeedRecyclerAdapter adapter = new FeedRecyclerAdapter((String url) -> {
            ClipData clip = ClipData.newPlainText("plain text", url);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, context.getText(R.string.url_copied_toast),
                    Toast.LENGTH_SHORT).show();
        });
        // setting the adapter on our recycler view
        binding.feedRecycler.setAdapter(adapter);


        viewModel.state.observe(this.getViewLifecycleOwner(), state -> {
            // TODO design a system for controlling the loading ui
            // if it is a successful UiState update our ui
            if (state.getClass() == UiState.Success.class) {
                binding.loadingBar.setVisibility(View.GONE);
                adapter.dataset.clear();
                adapter.dataset.addAll(((UiState.Success) state).feedNeoList);
                adapter.notifyDataSetChanged();
            }
            // if it is an error UiState display the error in a toast
            else if (state.getClass() == UiState.Error.class) {
                Toast.makeText(
                        context,
                        ((UiState.Error) state).e.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        return binding.getRoot();
    }
}
