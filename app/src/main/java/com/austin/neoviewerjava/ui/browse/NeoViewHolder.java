package com.austin.neoviewerjava.ui.browse;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.databinding.ItemNeoBinding;

public class NeoViewHolder extends RecyclerView.ViewHolder {
    ItemNeoBinding view;

    public NeoViewHolder(
            @NonNull ItemNeoBinding view, @NonNull ClipboardCopy copyLambda) {
        super(view.getRoot());
        this.view = view;

        // setting on click listeners for toggling the extra data card and the copy button
        view.getRoot().setOnClickListener(v -> toggleCardVisibility());
        view.jplCopyButton.setOnClickListener(v ->
                copyLambda.copy(view.neoJplUrl.getText().toString())
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

    public void resetCardVisibility() {
        view.cardView.setVisibility(View.GONE);
    }

    public void bind(Neo neo) {
        if (neo == null) {
            return;
        }

        view.neoName.setText(neo.name);
        view.neoDesignation.setText(neo.designation);
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
