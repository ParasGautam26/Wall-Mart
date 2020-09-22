package com.example.wallmart.Wallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;


import com.example.wallmart.R;
import com.example.wallmart.adapters.WallpaperAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WallpapersActivity extends AppCompatActivity {
    List<WallpaperViewModel> wallpaperList;
    RecyclerView recyclerView;
    WallpaperAdapter adapter;

    DatabaseReference dbwallpaper;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);
        wallpaperList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_wallpaper);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new WallpaperAdapter(this,wallpaperList);
        recyclerView.setAdapter(adapter);
        progressBar = findViewById(R.id.progressbar_wallpaper);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String category  = intent.getStringExtra("categories");

    /*   Toolbar toolbar = findViewById(R.id.toolbar_wallpaper);
        toolbar.setTitle(category);
        setSupportActionBar(toolbar);*/

        dbwallpaper = FirebaseDatabase.getInstance().getReference("images").child(category);
        dbwallpaper.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if(dataSnapshot.exists()){
                    for(DataSnapshot wallpaperSnapshot : dataSnapshot.getChildren()){
                        String name = wallpaperSnapshot.getKey();
                        String desc = wallpaperSnapshot.child("desc").getValue(String.class);
                        String thumb = wallpaperSnapshot.child("thumbnail").getValue(String.class);
                        WallpaperViewModel wallpaperViewModel = new WallpaperViewModel(name, desc, thumb);
                        wallpaperList.add(wallpaperViewModel);
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
