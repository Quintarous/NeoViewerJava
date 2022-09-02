package com.austin.neoviewerjava.ui.browse;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.databinding.ItemNeoBinding;

public class BrowseAdapter extends PagingDataAdapter<Neo, NeoViewHolder> {
    ClipboardCopy copyLambda;

    public BrowseAdapter(
            @NonNull DiffUtil.ItemCallback<Neo> diffCallback,
            @NonNull ClipboardCopy copyLambda
    ) {
        super(diffCallback);
        this.copyLambda = copyLambda;
    }

    @NonNull
    @Override
    public NeoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNeoBinding view = ItemNeoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new NeoViewHolder(view, copyLambda);
    }

    @Override
    public void onBindViewHolder(@NonNull NeoViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public void onViewRecycled(NeoViewHolder holder) {
        holder.resetCardVisibility();
    }
}
