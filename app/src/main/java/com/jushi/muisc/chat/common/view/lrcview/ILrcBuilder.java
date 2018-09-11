package com.jushi.muisc.chat.common.view.lrcview;


import com.jushi.muisc.chat.common.view.lrcview.impl.LrcRow;

import java.util.List;

/**
 * 解析歌词，得到LrcRow的集合
 */
public interface ILrcBuilder {
    List<LrcRow> getLrcRows(String rawLrc);
}
