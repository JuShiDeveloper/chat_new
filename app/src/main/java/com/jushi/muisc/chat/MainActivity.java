package com.jushi.muisc.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.muisc.chat.friends.login.LoginActivity;
import com.jushi.muisc.chat.music.play.play_navgation.PlayController;
import com.jushi.muisc.chat.sliding_menu.controller.SlidingMenuController;
import com.jushi.muisc.chat.utils.DisplayUtils;
import com.jushi.muisc.chat.music.utils.music.LocalMusicUtils;
import com.jushi.muisc.chat.utils.PATH;
import com.jushi.muisc.chat.view.layout.FriendsLayout;
import com.jushi.muisc.chat.view.main.MainTitleLayout;
import com.jushi.muisc.chat.view.layout.MusicLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private RelativeLayout contentContainer;
    private MainTitleLayout titleLayout;
    private NavigationView nav;
    private ImageView headerImage;
    private TextView landingTv;
    private Map<String, View> views = new HashMap<>();
    private final String TAG_MUAIS_LAYOUT = MusicLayout.class.getSimpleName();
    private final String TAG_FRIENDS_LAYOUT = FriendsLayout.class.getSimpleName();
    //播放控制栏
    private PlayController playController;
    //侧滑菜单控制类
    private SlidingMenuController menuController;
    //判断是否添加过本地歌曲数据
    private boolean isSetList = false;
    public static final String LOGIN_SUCCESS_KEY = "login_success";
    private final int REQUEST_LOGIN_PAGE_CODE = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        setContentView(R.layout.activity_main);
        initialize();
    }


    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
        initPlayController();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //SD卡读写权限请求
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            PATH.initPath();
        }
    }


    private void initialize() {
        initToolbar();
        findWidget();
        setTitleLatoutListener();
        initSlidingMenuController();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu_icon);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void findWidget() {
        titleLayout = findViewById(R.id.MainTitleLayout);
        contentContainer = findViewById(R.id.content_main_activity);
        contentContainer.addView(getMusicOrFriendsView(TAG_MUAIS_LAYOUT));

        nav = findViewById(R.id.nav_view);
        nav.setItemIconTintList(null);

        View headerView = nav.getHeaderView(0);
        headerImage = headerView.findViewById(R.id.header_imageView);
        landingTv = headerView.findViewById(R.id.login_tv);

    }

    //初始化播放控制栏
    private void initPlayController() {
        playController = PlayController.getInstance(this);
        if (!isSetList) {
            playController.setPlayList(LocalMusicUtils.getSongs(this));
            isSetList = true;
        }
        playController.showPlayControllerInfo();
    }

    private void initSlidingMenuController() {
        menuController = new SlidingMenuController(this);
        menuController.headerView(headerImage, landingTv);
    }

    //点击音乐或好友时相应的切换显示的内容
    private void setTitleLatoutListener() {
        titleLayout.setOnStateChangedListener(new MainTitleLayout.OnStateChangedListener() {
            @Override
            public void onStateChanged(int state) {
                switch (state) {
                    case 1:
                        contentContainer.removeAllViews();
                        contentContainer.addView(getMusicOrFriendsView(TAG_MUAIS_LAYOUT));
                        playController.setViewVisible(View.VISIBLE);
                        break;
                    case 2:
                        contentContainer.removeAllViews();
                        contentContainer.addView(getMusicOrFriendsView(TAG_FRIENDS_LAYOUT));
                        playController.setViewVisible(View.GONE);
                        break;
                }
            }
        });
    }

    private View getMusicOrFriendsView(String tag) {
        View view = views.get(tag);
        if (view == null) {
            if (tag.equals(TAG_MUAIS_LAYOUT)) {
                view = new MusicLayout(this);
            } else if (tag.equals(TAG_FRIENDS_LAYOUT)) {
                view = new FriendsLayout(this);
            }
        }
        views.put(tag, view);
        return view;
    }

    @Override
    public void onBackPressed() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                moveTaskToBack(true);
            }
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.local_music) { //本地音乐
            menuController.localMusic();
        } else if (id == R.id.near_play) {  //最近播放
            menuController.nearPlay();
        } else if (id == R.id.my_favorites) {  //我的收藏
            menuController.myFavorites();
        } else if (id == R.id.download_manager) {   //下载管理
            menuController.downloadManager();
        }

//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN_PAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_LOGIN_PAGE_CODE) {
            String loginName = data.getStringExtra(LOGIN_SUCCESS_KEY);
            menuController.showUserName(loginName);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playController.destory();
    }
}
