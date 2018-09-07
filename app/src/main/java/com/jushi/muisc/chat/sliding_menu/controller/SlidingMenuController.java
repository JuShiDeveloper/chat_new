package com.jushi.muisc.chat.sliding_menu.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jushi.muisc.chat.MainActivity;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.manager.ActivityManager;
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
    private TextView tvLogin;

    public SlidingMenuController(Context context) {
        this.context = context;
    }

    @Override
    public void headerView(ImageView headerImage, TextView loadingTv) {
        this.headerImage = headerImage;
        this.tvLogin = loadingTv;
        headerImage.setOnClickListener(this);
        loadingTv.setOnClickListener(this);
        checkIsLogin();
    }

    /**
     * 应用启动时检查是否已登录
     */
    private void checkIsLogin() {
        if (isLogin()) {
            showUserName(EMClient.getInstance().getCurrentUser());
        }
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
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.header_imageView:
                getPicture();
                break;
            case R.id.login_tv:
                if (isLogin()) { //如果已经登陆过，退出登录
                    EMClient.getInstance().logout(true,new EMCallBack(){

                        @Override
                        public void onSuccess() {
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    showUserName(context.getString(R.string.click_login));
                                }
                            });
                        }

                        @Override
                        public void onError(int code, String error) {

                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }
                    });
                    return;
                }
                toLoginActivity();
                break;
        }
    }

    private boolean isLogin() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    private void toLoginActivity() {
        ((MainActivity) context).toLoginActivity();
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

    public void showUserName(String userName) {
        tvLogin.setText(userName);
    }
}
