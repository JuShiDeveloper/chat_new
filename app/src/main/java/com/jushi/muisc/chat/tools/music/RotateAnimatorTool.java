package com.jushi.muisc.chat.tools.music;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.LinearInterpolator;



/**
 * Created by DuanJiaNing on 2017/6/13.
 * 控制切换动画
 */

public final class RotateAnimatorTool {

    private final View view;

    private final Context context;

    private final ValueAnimator rotateAnim;

    private boolean isSpin = false;

    public RotateAnimatorTool(Context context, final View view) {
        this.view = view;
        this.context = context;

        rotateAnim = ObjectAnimator.ofFloat(0, 360);
        rotateAnim.setDuration(45 * 1000);
        rotateAnim.setRepeatMode(ValueAnimator.RESTART);
        rotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setRotation(value);
            }
        });
    }

    public void startSpin() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (rotateAnim.isPaused()) {
                rotateAnim.resume();
            } else {
                rotateAnim.start();
            }
        } else {
            rotateAnim.start();
        }
        isSpin = true;
    }

    public void stopSpin() {
        if (rotateAnim.isRunning()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                rotateAnim.pause();
            } else {
                rotateAnim.cancel();
            }
            isSpin = false;
        }
    }

    public boolean isSpin() {
        return isSpin;
    }

}
