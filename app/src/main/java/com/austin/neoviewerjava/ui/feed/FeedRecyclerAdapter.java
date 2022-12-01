package com.austin.neoviewerjava.ui.feed;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.austin.neoviewerjava.database.FeedNeo;
import com.austin.neoviewerjava.databinding.ItemFeedNeoBinding;
import com.austin.neoviewerjava.ui.browse.ClipboardCopy;

import java.util.ArrayList;
import java.util.List;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedNeoViewHolder> {

    private final ClipboardCopy copyLambda;
    public List<FeedNeo> dataset = new ArrayList<>();

    public FeedRecyclerAdapter(@NonNull ClipboardCopy copyLambda) {
        this.copyLambda = copyLambda;
    }

    @NonNull
    @Override
    public FeedNeoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeedNeoBinding viewBinding = ItemFeedNeoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new FeedNeoViewHolder(viewBinding, copyLambda);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedNeoViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
