package com.jushi.muisc.chat.common.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by paocai on 2018/5/7.
 */

public class ToastUtils {

    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
