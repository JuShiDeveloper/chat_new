package com.jushi.muisc.chat.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by paocai on 2018/5/5.
 */

public class JSGridLayoutManager extends GridLayoutManager {

    private boolean isScrollEnable = true;

    public JSGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public JSGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public JSGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollEnable(boolean isScrollEnable){
        this.isScrollEnable = isScrollEnable;
    }

    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnable && super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnable && super.canScrollVertically();
    }
}
