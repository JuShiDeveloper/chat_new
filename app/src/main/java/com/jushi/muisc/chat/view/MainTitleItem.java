package com.jushi.muisc.chat.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.R;

/**
 * Created by paocai on 2018/5/4.
 */

public class MainTitleItem extends LinearLayout {

    private Context mContext;
    private JSTextView textView;
    public MainTitleItem(Context context) {
        this(context,null);
    }

    public MainTitleItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MainTitleItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_main_title_item,this);
//        View.inflate(mContext, R.layout.layout_main_title_item,this);
        textView = findViewById(R.id.JSTextView);
    }

    public void setTextColor(int color){
        textView.setTextColor(color);
    }
    public void setText(String text){
        textView.setText(text);
    }
}
