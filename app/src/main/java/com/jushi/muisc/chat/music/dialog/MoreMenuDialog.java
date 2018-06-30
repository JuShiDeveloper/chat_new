package com.jushi.muisc.chat.music.dialog;

import android.app.Dialog;
import android.content.Context;

import com.jushi.muisc.chat.R;

public class MoreMenuDialog {
    private Context context;
    private Dialog dialog;

    public MoreMenuDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.Menu_Dialog_Style);
    }
}
