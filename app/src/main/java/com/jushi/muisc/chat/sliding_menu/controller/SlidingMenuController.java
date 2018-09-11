package com.jushi.muisc.chat.sliding_menu.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jushi.muisc.chat.common.utils.SaveUtils;
import com.jushi.muisc.chat.main.MainActivity;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.settings.SettingsActivity;
import com.jushi.muisc.chat.sliding_menu.localmusic.LocalMusicActivity;
import com.jushi.muisc.chat.sliding_menu.download_manager.DownloadActivity;
import com.jushi.muisc.chat.sliding_menu.my_favorites.MyFavoritesActivity;
import com.jushi.muisc.chat.sliding_menu.near_play.NearPlayActivity;
import com.jushi.muisc.chat.sliding_menu.common.minterface.IController;
import com.jushi.muisc.chat.common.transform.CircleTransform;
import com.jushi.muisc.chat.common.utils.ToastUtils;
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
    private RelativeLayout imageLayout;
    public static final String LOGIN_NAME_KEY = "login_name";


    public SlidingMenuController(Context context) {
        this.context = context;
        context.registerReceiver(loginBroadcastReceiver,
                new IntentFilter(context.getString(R.string.LOGIN_SUCCESS_BROADCAST_ACTION)));
    }

    @Override
    public void headerView(ImageView headerImage, TextView loadingTv, RelativeLayout imageLayout) {
        this.headerImage = headerImage;
        this.tvLogin = loadingTv;
        this.imageLayout = imageLayout;
        headerImage.setOnClickListener(this);
        loadingTv.setOnClickListener(this);
        checkIsLogin();
        checkSaveUserImage();
    }

    /**
     * 判断用户是否有设置过头像
     */
    private void checkSaveUserImage() {
        String userImage = SaveUtils.getInstance(context).getSaveUserImage();
        if (userImage == null) return;
        showUserImage(userImage);
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
                if (isLogin()) { //如果已经登陆过，跳转到设置页面
                    ActivityManager.startSettingsActivity(context);
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
                showUserImage(photo.getOriginalFile().getPath());
                SaveUtils.getInstance(context).saveUserImage(photo.getOriginalFile().getPath());
            }
        }, false);
    }

    private void showUserImage(String imagePath) {
        Glide.with(context)
                .load(imagePath)
                .transform(new CircleTransform(context))
                .error(R.drawable.round_header)
                .into(headerImage);
        imageLayout.setBackgroundResource(R.drawable.bg_header_container);
    }

    public void showUserName(String userName) {
        tvLogin.setText(userName);
    }

    public void unRegistReceiver() {
        context.unregisterReceiver(loginBroadcastReceiver);
    }

    private BroadcastReceiver loginBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == context.getString(R.string.LOGIN_SUCCESS_BROADCAST_ACTION)) {
                showUserName(intent.getStringExtra(LOGIN_NAME_KEY));
            }
        }
    };
}
