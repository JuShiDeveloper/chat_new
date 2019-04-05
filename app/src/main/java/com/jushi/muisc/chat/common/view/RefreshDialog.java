package com.jushi.muisc.chat.common.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jushi.muisc.chat.R;

public class RefreshDialog extends Dialog {
    private AnimationDrawable animationDrawable;

    public RefreshDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public RefreshDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected RefreshDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), R.layout.refresh_head_layout, null);
        rootView.findViewById(R.id.refresh_logo_image).setVisibility(View.VISIBLE);
        ImageView refreshView = rootView.findViewById(R.id.refresh_head_image);
        setViewAnimationDrawable(refreshView);
        RelativeLayout ll = rootView.findViewById(R.id.refresh_layout);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(layoutParams);
        setContentView(rootView);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private void setViewAnimationDrawable(ImageView refreshView) {
        animationDrawable = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.refresh_head_drawable);
        refreshView.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();
    }

    public void showDialog() {
        this.show();
    }

    public void dismissDialog() {
        this.dismiss();
    }
}
