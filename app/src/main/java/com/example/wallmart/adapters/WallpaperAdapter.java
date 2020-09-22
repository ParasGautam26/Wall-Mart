package com.example.wallmart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.wallmart.PreviewActivity;
import com.example.wallmart.R;
import com.example.wallmart.Wallpapers.WallpaperViewModel;


import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.CategoriesViewHolder> {
    private Context context;
    private List<WallpaperViewModel> wallpaperList;
    public WallpaperAdapter(Context context, List<WallpaperViewModel> wallpaperList) {
        this.context = context;
        this.wallpaperList = wallpaperList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_wallpaper,parent,false);
        return new CategoriesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        WallpaperViewModel wallpaperViewModel = wallpaperList.get(position);
       RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with((context))
                .load(wallpaperViewModel.thumb)
                .apply(requestOptions)
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_wallpaper);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            WallpaperViewModel w = wallpaperList.get(p);
            Intent intent = new Intent(context, PreviewActivity.class);
            intent.putExtra("image",w.thumb);
            context.startActivity(intent);
        }
    }
}
