package com.jushi.muisc.chat.view.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.search.ui.SearchActivity;
import com.jushi.muisc.chat.manager.ActivityManager;

/**
 * Created by paocai on 2018/5/4.
 */

public class MainTitleLayout extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private MainTitleItem musicButton, friendsButton;
    private ImageView searchButton;
    public static final int MUSIC_STATE = 1;
    public static final int FRIENDS_STATE = MUSIC_STATE + 1;
    private int state;
    private OnStateChangedListener listener;

    public MainTitleLayout(Context context) {
        this(context, null);
    }

    public MainTitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        findWidget();
        setClickListener();
    }

    private void findWidget() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_main_title, this);
        musicButton = findViewById(R.id.music_layout_main_title);
        musicButton.setText(getResources().getString(R.string.music));
        musicButton.setTextColor(getResources().getColor(R.color.white));
        friendsButton = findViewById(R.id.friends_layout_main_title);
        friendsButton.setText(getResources().getString(R.string.friends));
        searchButton = findViewById(R.id.search_layout_main_title);
    }

    private void setClickListener() {
        musicButton.setOnClickListener(this);
        friendsButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_layout_main_title:
                state = MUSIC_STATE;
                break;
            case R.id.friends_layout_main_title:
                state = FRIENDS_STATE;
                break;
            case R.id.search_layout_main_title:
                ActivityManager.startActivity(mContext, SearchActivity.class);
                break;
        }
        if (listener != null) {
            listener.onStateChanged(state);
        }
        stateChanged(state);
    }

    private void stateChanged(int state) {
        switch (state) {
            case MUSIC_STATE:
                musicButton.setBackgroundResource(R.drawable.botton1);
                musicButton.setTextColor(getResources().getColor(R.color.white));
                friendsButton.setBackgroundResource(0);
                friendsButton.setTextColor(getResources().getColor(R.color._999999));
                break;
            case FRIENDS_STATE:
                musicButton.setBackgroundResource(0);
                musicButton.setTextColor(getResources().getColor(R.color._999999));
                friendsButton.setBackgroundResource(R.drawable.botton1);
                friendsButton.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        this.listener = listener;
    }

    public interface OnStateChangedListener {
        void onStateChanged(int state);
    }
}
