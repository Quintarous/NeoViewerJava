package com.austin.neoviewerjava.ui.browse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;

import com.austin.neoviewerjava.R;
import com.austin.neoviewerjava.databinding.ItemLoadstateStatusBinding;

public class BrowseLoadStateViewHolder extends RecyclerView.ViewHolder {

    ItemLoadstateStatusBinding view;

    public BrowseLoadStateViewHolder(
            @NonNull ItemLoadstateStatusBinding view,
            @NonNull View.OnClickListener retryCallback
    ) {
        super(view.getRoot());
        this.view = view;

        // setting the onClickListener for the retry button
        view.retryButton.setOnClickListener(retryCallback);
    }

    public void bind(LoadState state) {
        view.loadstateProgressbar.setVisibility(state instanceof LoadState.Loading
                ? View.VISIBLE : View.GONE);

        view.errorLabel.setVisibility(state instanceof LoadState.Error
                ? View.VISIBLE : View.GONE);

        // setting the text of the error message if the state is an error
        if (state instanceof LoadState.Error) {
            LoadState.Error errorState = (LoadState.Error) state;
            view.errorMessage.setText(errorState.getError().getLocalizedMessage());
        }
        view.errorMessage.setVisibility(state instanceof LoadState.Error
                ? View.VISIBLE : View.GONE);

        view.retryButton.setVisibility(state instanceof LoadState.Error
                ? View.VISIBLE : View.GONE);
    }
}
