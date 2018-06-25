package com.jushi.muisc.chat.view.lrcview;


import com.jushi.muisc.chat.view.lrcview.impl.LrcRow;

import java.util.List;

/**
 * 解析歌词，得到LrcRow的集合
 */
public interface ILrcBuilder {
    List<LrcRow> getLrcRows(String rawLrc);
}
