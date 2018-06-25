package com.jushi.muisc.chat.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jushi.muisc.chat.R;

/**
 * Created by paocai on 2018/5/5.
 */

public class RefreshHeadView extends RelativeLayout{

    private ImageView refreshView;
    private AnimationDrawable animationDrawable;

    public RefreshHeadView(Context context) {
        this(context,null);
    }

    public RefreshHeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.refresh_head_layout,this);
        refreshView = findViewById(R.id.refresh_head_image);
//        setViewAnimation();
        setViewAnimationDrawable();
    }

    private void setViewAnimationDrawable(){
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.refresh_head_drawable);
        refreshView.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();
    }

    private void setViewAnimation() {
        RotateAnimation animation = new RotateAnimation(360,0,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(1300);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(getContext(),android.R.interpolator.linear);
        refreshView.startAnimation(animation);
    }
}
