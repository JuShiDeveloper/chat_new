package com.jushi.muisc.chat.common.view.lrcview;


import com.jushi.muisc.chat.common.view.lrcview.impl.LrcRow;

/**
 * 歌词拖动时候的监听类
 */
public interface ILrcViewListener {
    /**
     * 当歌词被用户上下拖动的时候回调该方法
     */
    void onLrcSeeked(int newPosition, LrcRow row);
}
