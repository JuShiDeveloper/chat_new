package com.jushi.muisc.chat.music.common.jsinterface;

/**
 * 下载监听接口
 */
public interface DownloadListener {
    /**
     * 更新下载进度
     * @param progress
     */
    void onDownloading(int progress);
    /**
     * 下载成功
     */
    void onDownloadSuccess();
    /**
     * 下载失败
     */
    void onDownloadFailed();

}
