package com.example.wallmart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.wallmart.R;
import com.example.wallmart.Wallpapers.WallpapersActivity;
import com.example.wallmart.ui.categories.CategoriesViewModel;



import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private Context context;
    private List<CategoriesViewModel> categoryList;

   // private InterstitialAd mInterstitialAd;

    public CategoriesAdapter(Context context, List<CategoriesViewModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;

   /*     mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());            */
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_categories,parent,false);
        return new CategoriesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        CategoriesViewModel categoriesViewModel = categoryList.get(position);
        holder.textView.setText(categoriesViewModel.name);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(categoriesViewModel.thumb)
                .apply(requestOptions)
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView textView;
        ImageView imageView;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_cat_name);
            imageView = itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

     /*     if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Toast.makeText(context, "Ad not loaded", Toast.LENGTH_SHORT).show();
            }*/
            int p = getAdapterPosition();
            CategoriesViewModel c = categoryList.get(p);
            Intent intent = new Intent(context, WallpapersActivity.class);
            intent.putExtra("categories",c.name);
            context.startActivity(intent);
        }
    }
}
