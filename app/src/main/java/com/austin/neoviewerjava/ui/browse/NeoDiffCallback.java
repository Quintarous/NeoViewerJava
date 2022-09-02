package com.austin.neoviewerjava.ui.browse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.austin.neoviewerjava.database.Neo;

public class NeoDiffCallback extends DiffUtil.ItemCallback<Neo>{
    @Override
    public boolean areItemsTheSame(@NonNull Neo oldItem, @NonNull Neo newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Neo oldItem, @NonNull Neo newItem) {
        return oldItem.equals(newItem);
    }
}
