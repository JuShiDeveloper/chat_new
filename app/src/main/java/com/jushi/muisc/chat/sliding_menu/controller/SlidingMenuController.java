package com.jushi.muisc.chat.sliding_menu.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.landing.LandingActivity;
import com.jushi.muisc.chat.sliding_menu.localmusic.ui.LocalMusicActivity;
import com.jushi.muisc.chat.sliding_menu.download_manager.DownloadActivity;
import com.jushi.muisc.chat.sliding_menu.my_favorites.MyFavoritesActivity;
import com.jushi.muisc.chat.sliding_menu.near_play.NearPlayActivity;
import com.jushi.muisc.chat.sliding_menu.minterface.IController;
import com.jushi.muisc.chat.transform.CircleTransform;
import com.jushi.pictures.camera.OnPicturePathListener;
import com.jushi.pictures.camera.PictureCapture;
import com.jushi.pictures.camera.utils.Photo;

/**
 * 侧滑菜单控制类
 */
public class SlidingMenuController implements IController, View.OnClickListener {
    private Context context;
    private ImageView headerImage;

    public SlidingMenuController(Context context) {
        this.context = context;
    }

    @Override
    public void headerView(ImageView headerImage, TextView loadingTv) {
        this.headerImage = headerImage;
        headerImage.setOnClickListener(this);
        loadingTv.setOnClickListener(this);
    }


    @Override
    public void localMusic() {
        ActivityManager.startActivity(context, LocalMusicActivity.class);
    }

    @Override
    public void nearPlay() {
        ActivityManager.startActivity(context, NearPlayActivity.class);
    }

    @Override
    public void myFavorites() {
        ActivityManager.startActivity(context, MyFavoritesActivity.class);
    }

    @Override
    public void downloadManager() {
        ActivityManager.startActivity(context, DownloadActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_imageView:
                getPicture();
                break;
            case R.id.landing_tv:
                toRegisterActivity();
                break;
        }
    }

    private void toRegisterActivity() {
        Intent intent = new Intent(context, LandingActivity.class);
        context.startActivity(intent);
    }

    private void getPicture() {
        PictureCapture.INSTANCE.getPicture(context, new OnPicturePathListener() {
            @Override
            public void onPhoto(Photo photo) {
                Glide.with(context)
                        .load(Uri.fromFile(photo.getOriginalFile()))
                        .transform(new CircleTransform(context))
                        .into(headerImage);
            }
        }, false);
    }
}
