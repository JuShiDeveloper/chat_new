package com.jushi.muisc.chat.common.utils;

import android.content.Context;

import com.jushi.muisc.chat.common.view.RefreshDialog;

public class RefreshViewUtils {

    private static RefreshDialog refreshDialog;

    public static void showRefreshDialog(Context context) {
        refreshDialog = new RefreshDialog(context);
        refreshDialog.showDialog();
    }

    public static void dismissRefreshDialog() {
        refreshDialog.dismissDialog();
    }
}
