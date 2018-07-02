package com.jushi.muisc.chat.music.utils;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {

    public static void translateAnimation(){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,1f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0);
    }
}
