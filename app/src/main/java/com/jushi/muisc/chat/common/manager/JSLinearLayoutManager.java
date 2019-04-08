package com.jushi.muisc.chat.common.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class JSLinearLayoutManager extends LinearLayoutManager {
    public JSLinearLayoutManager(Context context) {
        super(context);
    }

    public JSLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public JSLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollHorizontally() {
        return false && super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return false && super.canScrollVertically();
    }
}
