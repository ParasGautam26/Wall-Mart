package com.example.wallmart.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallmart.R;
import com.example.wallmart.adapters.CategoriesAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    private List<CategoriesViewModel> categoryList;
    private ProgressBar progressBar;
    private DatabaseReference dbCategories;
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     //   MobileAds.initialize(getActivity(),"ca-app-pub-8647298360484656~2601740973");

        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        categoryList = new ArrayList<>();
        adapter = new CategoriesAdapter(getActivity(),categoryList);
        recyclerView.setAdapter(adapter);
        dbCategories = FirebaseDatabase.getInstance().getReference("categories");
        dbCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if(dataSnapshot.exists()) {
                  progressBar.setVisibility(View.GONE);
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                      String name = ds.getKey();
                      String desc = ds.child("desc").getValue(String.class);
                      String thumb = ds.child("thumbnail").getValue(String.class);
                      CategoriesViewModel categoriesViewModel = new CategoriesViewModel(name, desc, thumb);
                      categoryList.add(categoriesViewModel);
                  }
                  adapter.notifyDataSetChanged();
              }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
