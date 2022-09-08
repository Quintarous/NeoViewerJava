package com.austin.neoviewerjava.ui.browse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;

import com.austin.neoviewerjava.databinding.ItemLoadstateStatusBinding;

public class BrowseLoadStateAdapter extends LoadStateAdapter<BrowseLoadStateViewHolder> {

    View.OnClickListener retryCallback;

    public BrowseLoadStateAdapter(@NonNull View.OnClickListener retryCallback) {
        this.retryCallback = retryCallback;
    }

    @NonNull
    @Override
    public BrowseLoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull LoadState loadState) {
        ItemLoadstateStatusBinding view = ItemLoadstateStatusBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new BrowseLoadStateViewHolder(view, retryCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseLoadStateViewHolder holder, @NonNull LoadState loadState) {
        holder.bind(loadState);
    }
}
