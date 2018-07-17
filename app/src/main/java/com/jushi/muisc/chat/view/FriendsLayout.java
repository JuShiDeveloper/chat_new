package com.jushi.muisc.chat.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.jushi.muisc.chat.R;

/**
 * Created by wyf on 2018/5/4.
 */

public class FriendsLayout extends RelativeLayout implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    public FriendsLayout(Context context) {
        this(context, null);
    }

    public FriendsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        findWidget();
        setListener();
    }

    private void findWidget() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_friends, this);
        navigationView = findViewById(R.id.BottomNavigationView);
        viewPager = findViewById(R.id.friends_ViewPager);
    }

    private void setListener(){
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.news: //消息
                break;
            case R.id.my_friends:  //好友
                break;
            case R.id.my_: //我的
                break;
        }
        return false;
    }
}
