package com.example.wallmart.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallmart.R;
import com.example.wallmart.adapters.FavouritesAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private List<FavouritesViewModel> favouriteList;
    private RecyclerView recyclerView;
    private FavouritesAdapter adapter;
    private LinearLayout linearLayout;
    private String url;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favouriteList = new ArrayList<>();
        linearLayout = view.findViewById(R.id.linear_layout_fav);
        linearLayout.setVisibility(View.VISIBLE);
        FavouritesViewModel favouritesViewModel = new FavouritesViewModel();
        if(getArguments()!=null) {
            url = getArguments().getString("link");
            linearLayout.setVisibility(View.GONE);
            favouritesViewModel.setThumb(url);
        }
        recyclerView = view.findViewById(R.id.recycler_view_favourites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new FavouritesAdapter(getActivity(),favouriteList);
        recyclerView.setAdapter(adapter);
    }
}
