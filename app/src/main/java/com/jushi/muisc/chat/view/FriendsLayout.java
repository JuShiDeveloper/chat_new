package com.jushi.muisc.chat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.jushi.muisc.chat.R;

/**
 * Created by paocai on 2018/5/4.
 */

public class FriendsLayout extends RelativeLayout {

    public FriendsLayout(Context context) {
        this(context,null);
    }

    public FriendsLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FriendsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        findWidget();
    }

    private void findWidget() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_friends,this);
    }
}
