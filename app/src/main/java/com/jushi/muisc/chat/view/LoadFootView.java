package com.jushi.muisc.chat.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jushi.muisc.chat.R;

/**
 * Created by paocai on 2018/5/6.
 */

public class LoadFootView extends RelativeLayout {

    private AnimationDrawable animationDrawable;
    private ImageView imageView;

    public LoadFootView(Context context) {
        this(context,null);
    }

    public LoadFootView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View.inflate(getContext(), R.layout.load_foot_view_layout,this);
        imageView = findViewById(R.id.load_foot_view_imageView);
        setIvDrawable();
    }

    private void setIvDrawable() {
        animationDrawable = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.load_foot_drawable);
        imageView.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();
    }


}
