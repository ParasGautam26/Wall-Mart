package com.example.wallmart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.example.wallmart.R;
import com.example.wallmart.ui.favourites.FavouritesViewModel;


import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavViewHolder> {

    private Context context;
    private List<FavouritesViewModel> favList;

    public FavouritesAdapter(Context context, List<FavouritesViewModel> favList) {
        this.context = context;
        this.favList = favList;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_favourites,parent,false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
            FavouritesViewModel favouritesViewModel = favList.get(position);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(favouritesViewModel.thumb)
                .apply(requestOptions)
                .into(holder.image_View);
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image_View;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            image_View = itemView.findViewById(R.id.image_view_recycler_favourites);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
        }
    }
}
