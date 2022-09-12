package com.austin.neoviewerjava.ui.browse;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.austin.neoviewerjava.R;
import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.databinding.FragmentBrowseBinding;
import com.austin.neoviewerjava.network.NeoService;
import com.austin.neoviewerjava.repository.NeoRepository;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Unit;
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


        // adding a divider line in between every item for readability
        DividerItemDecoration divider =
                new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        binding.browseRecycler.addItemDecoration(divider);


        // getting the clipboard manager
        ClipboardManager clipboard = context.getSystemService(ClipboardManager.class);
        // creating the BrowseAdapter
        BrowseAdapter adapter = new BrowseAdapter(new NeoDiffCallback(), (String url) -> {
            ClipData clip = ClipData.newPlainText("plain text", url);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, context.getText(R.string.url_copied_toast),
                    Toast.LENGTH_SHORT).show();
        });
        // setting up the header/footer load state displays on the BrowseAdapter
        ConcatAdapter concatAdapter = adapter.withLoadStateHeaderAndFooter(
                new BrowseLoadStateAdapter(view -> adapter.retry()),
                new BrowseLoadStateAdapter(view -> adapter.retry())
        );


        // setting the on click listener for the error retry button and swipe refresh gesture
        binding.browseRetryButton.setOnClickListener( view -> adapter.retry() );
        binding.swipeRefresh.setOnRefreshListener(adapter::refresh);


        // adding a listener to toggle the visibility of loading/error UI elements
        adapter.addLoadStateListener(loadStates -> {
            // toggling the visibility of the loading/error UI
            binding.browseProgressBar.setVisibility(loadStates.getRefresh()
                    instanceof LoadState.Loading ? View.VISIBLE : View.GONE);

            binding.browseErrorLabel.setVisibility(loadStates.getRefresh()
                    instanceof LoadState.Error ? View.VISIBLE : View.GONE);

            binding.browseRetryButton.setVisibility(loadStates.getRefresh()
                    instanceof LoadState.Error ? View.VISIBLE : View.GONE);

            // setting the error text to the error label and disabling the swipe refresh UI if needed
            if (loadStates.getRefresh() instanceof LoadState.Error) {
                LoadState.Error e = (LoadState.Error) loadStates.getRefresh();
                binding.browseErrorLabel.setText(e.getError().getLocalizedMessage());
                if (binding.swipeRefresh.isRefreshing()) {
                    binding.swipeRefresh.setRefreshing(false);
                }
            // else if we're not loading disable the swipe refresh UI
            } else if (loadStates.getRefresh() instanceof LoadState.NotLoading) {
                if (binding.swipeRefresh.isRefreshing()) {
                    binding.swipeRefresh.setRefreshing(false);
                }
            }

            return Unit.INSTANCE;
        });


        //setting the adapter on the recycler view
        binding.browseRecycler.setAdapter(concatAdapter);
        // subscribing to the data flow from the view model and submitting the emitted PagingData
        pagingDataDisposable = browseViewModel.dataFlow
                .subscribe(pagingData -> {
                    adapter.submitData(getLifecycle(), pagingData);
                });


        // setting up the options menu for this fragment
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.addMenuProvider(new MenuProvider() {
                @Override
                public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                    menuInflater.inflate(R.menu.main, menu);
                }

                @Override
                public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.action_refresh) {
                        adapter.refresh();
                        return true;
                    }
                    return false;
                }
            }, getViewLifecycleOwner());
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pagingDataDisposable.dispose();
        binding = null;
    }
}