package com.jushi.muisc.chat.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by paocai on 2018/5/4.
 */

@SuppressLint("AppCompatCustomView")
public class JSTextView extends TextView {
    public JSTextView(Context context) {
        super(context);
    }

    public JSTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JSTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Typeface createTypeface(Context context,String fontPath){
        return Typeface.createFromAsset(context.getAssets(),fontPath);
    }

    @Override
    public void setTypeface(Typeface tf,int style) {
        super.setTypeface(createTypeface(getContext(),"fonts/Roboto-Medium.ttf"),style);
    }
}
