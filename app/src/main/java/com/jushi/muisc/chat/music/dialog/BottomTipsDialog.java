package com.jushi.muisc.chat.music.dialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.view.JSTextView;

/**
 * 在底部显示删除  取消  两个选项的dialog
 */
public class BottomTipsDialog {

    private static OnOkBtnClickListener onListener;
    private static BottomBuilder buttomBuilder;
    private static BottomSheetDialog dialog;
    private static JSTextView okBtn, cancelBtn;

    public static BottomBuilder create(Context context) {
        dialog = new BottomSheetDialog(context);
        View rootView = View.inflate(context,R.layout.bottom_dialog_layout, null);
        okBtn = rootView.findViewById(R.id.tv_OK);
        cancelBtn = rootView.findViewById(R.id.tv_cancel);
        dialog.setContentView(rootView);
        buttomBuilder = new BottomBuilder();
        return buttomBuilder;
    }

    public interface OnOkBtnClickListener {
        void onDeleteBtnClick(JSTextView view);
    }

    public static class BottomBuilder implements View.OnClickListener {

        public BottomBuilder setListener(OnOkBtnClickListener listener) {
            onListener = listener;
            okBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);
            return buttomBuilder;
        }

        public BottomBuilder setOkBtnText(String text) {
            okBtn.setText(text);
            return buttomBuilder;
        }

        public BottomBuilder setCancelBtnText(String text) {
            cancelBtn.setText(text);
            return buttomBuilder;
        }


        public void show() {
            dialog.show();
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_OK:
                    if (onListener != null) {
                        onListener.onDeleteBtnClick(okBtn);
                    }
                    dialog.dismiss();
                    break;
                case R.id.tv_cancel:
                    dialog.dismiss();
                    break;
            }
        }
    }

}
