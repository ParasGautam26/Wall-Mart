package com.example.wallmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wallmart.ui.favourites.FavouritesFragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PreviewActivity extends AppCompatActivity {
    ImageView imageView;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton set,download,share,fav;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview);

        imageView = findViewById(R.id.image_view_preview);
        Intent intent = getIntent();
        url = intent.getStringExtra("image");
        Glide.with(this).load(url).into(imageView);
        floatingActionMenu = findViewById(R.id.floating_menu);
        set = findViewById(R.id.set);
        download = findViewById(R.id.download);
        share = findViewById(R.id.share);
        fav = findViewById(R.id.add_fav);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    manager.setBitmap(bitmap);
                    Toast.makeText(PreviewActivity.this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(PreviewActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                floatingActionMenu.close(true);
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadimage();
                floatingActionMenu.close(true);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
                floatingActionMenu.close(true);
            }
        });


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("link",url);
                FavouritesFragment favouritesFragment = new FavouritesFragment();
                favouritesFragment.setArguments(data);
                Toast.makeText(PreviewActivity.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                floatingActionMenu.close(true);
            }
        });
    }

    private void downloadimage(){
            Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri uri = saveWallpaperAndGetUri(bitmap);
                            if(uri!=null){
                                    intent.setDataAndType(uri,"image/*");
                                    startActivity(intent.createChooser(intent,"Wall Mart"));
                                    Toast.makeText(PreviewActivity.this, "Wallpaper Downloaded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

    private Uri saveWallpaperAndGetUri(Bitmap bitmap){
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                    intent.setData(uri);
                    this.startActivity(intent);

            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
            return null;
        }

        File folder = new File(Environment.getExternalStorageDirectory().toString()+"/Wall_Mart");
        folder.mkdirs();
        File file = new File(folder,"image"+".jpg");

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();
            return Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void shareImage(){
        Bitmap bitmap = getBitmapfromView(imageView);
        try {
            File file = new File(this.getExternalCacheDir(),"black.png");
            FileOutputStream fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fout);
            fout.close();
            fout.close();
            file.setReadable(true,false);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(PreviewActivity.this,BuildConfig.APPLICATION_ID+".provider",file));
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent,"Share Image Via"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private Bitmap getBitmapfromView(View view){
        Bitmap returnBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnBitmap);
        Drawable bgDrawable = view.getBackground();
        if(bgDrawable!=null){
            bgDrawable.draw(canvas);
        }
        else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return  returnBitmap;
    }
}
