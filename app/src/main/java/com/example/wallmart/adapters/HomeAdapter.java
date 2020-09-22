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
import com.example.wallmart.PreviewActivity;
import com.example.wallmart.R;
import com.example.wallmart.ui.home.HomeViewModel;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private Context context;
    private List<HomeViewModel> homeList;

    public HomeAdapter(Context context, List<HomeViewModel> homeList) {
        this.context = context;
        this.homeList = homeList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_home,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
            HomeViewModel homeViewModel = homeList.get(position);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(homeViewModel.thumb)
                .apply(requestOptions)
                .into(holder.image_View);
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image_View;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            image_View = itemView.findViewById(R.id.image_view_recycler_home);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            HomeViewModel h = homeList.get(p);
            Intent intent = new Intent(context, PreviewActivity.class);
            intent.putExtra("image",h.thumb);
            context.startActivity(intent);
        }
    }
}
