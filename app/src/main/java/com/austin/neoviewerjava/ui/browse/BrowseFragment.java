package com.austin.neoviewerjava.ui.browse;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;

import com.austin.neoviewerjava.R;
import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.databinding.FragmentBrowseBinding;
import com.austin.neoviewerjava.network.NeoService;
import com.austin.neoviewerjava.repository.NeoRepository;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@AndroidEntryPoint
public class BrowseFragment extends Fragment {

    private FragmentBrowseBinding binding;
    private Disposable pagingDataDisposable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // getting the ViewModel
        BrowseViewModel browseViewModel =
                new ViewModelProvider(this).get(BrowseViewModel.class);

        // inflating the binding
        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        Context context = requireContext();

        // getting the clipboard manager
        ClipboardManager clipboard = context.getSystemService(ClipboardManager.class);

        // creating the adapter
        BrowseAdapter adapter = new BrowseAdapter(new NeoDiffCallback(), (String url) -> {
            ClipData clip = ClipData.newPlainText("plain text", url);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, context.getText(R.string.url_copied_toast),
                    Toast.LENGTH_SHORT).show();
        });

        //setting the adapter on the recycler view
        binding.browseRecycler.setAdapter(adapter);

        pagingDataDisposable = browseViewModel.dataFlow
                .subscribe(pagingData -> {
            adapter.submitData(getLifecycle(), pagingData);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pagingDataDisposable.dispose();
        binding = null;
    }
}