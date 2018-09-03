package com.jushi.muisc.chat.music.utils.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {

    public static void translateAnimation(View view) {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        view.startAnimation(animation);
    }
}
