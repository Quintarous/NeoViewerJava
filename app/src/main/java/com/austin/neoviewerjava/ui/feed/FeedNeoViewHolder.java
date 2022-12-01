package com.austin.neoviewerjava.ui.feed;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.austin.neoviewerjava.database.FeedNeo;
import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.databinding.ItemFeedNeoBinding;
import com.austin.neoviewerjava.ui.browse.ClipboardCopy;

public class FeedNeoViewHolder extends RecyclerView.ViewHolder {

    private final ItemFeedNeoBinding view;

    public FeedNeoViewHolder(@NonNull ItemFeedNeoBinding view, @NonNull ClipboardCopy copyLambda) {
        super(view.getRoot());
        this.view = view;

        view.getRoot().setOnClickListener(v -> toggleCardVisibility());
        view.jplCopyButton.setOnClickListener(v ->
                copyLambda.copy(view.neoJplUrl.toString())
        );
    }

    // toggles the visibility of the card in our layout from gone to visible and vice versa
    private void toggleCardVisibility() {
        int newVisibility;
        if (view.cardView.getVisibility() == View.VISIBLE) {
            newVisibility = View.GONE;
        } else {
            newVisibility = View.VISIBLE;
        }
        view.cardView.setVisibility(newVisibility);
    }

    // resets the cards visibility to gone. Called by our host adapter when this view is recycled
    public void resetCardVisibility() {
        view.cardView.setVisibility(View.GONE);
    }

    public void bind(FeedNeo neo) {
        if (neo == null) {
            return;
        }

        view.neoName.setText(neo.name);
        view.neoJplUrl.setText(neo.jplUrl);
        view.neoHazardous.setText(neo.hazardous.toString());

        view.neoKilometersMin.setText(neo.kilometersMin.toString());
        view.neoKilometersMax.setText(neo.kilometersMax.toString());

        view.neoMetersMin.setText(neo.metersMin.toString());
        view.neoMetersMax.setText(neo.metersMax.toString());

        view.neoMilesMin.setText(neo.milesMin.toString());
        view.neoMilesMax.setText(neo.milesMax.toString());

        view.neoFeetMin.setText(neo.feetMin.toString());
        view.neoFeetMax.setText(neo.feetMax.toString());
    }
}
