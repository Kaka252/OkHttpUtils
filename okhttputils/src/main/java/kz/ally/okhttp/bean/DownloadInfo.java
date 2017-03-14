package kz.ally.okhttp.bean;

import android.content.Context;

import java.io.File;

/**
 * 作者：ZhouYou
 * 日期：2017/3/14.
 * 用于断电续传下载的信息类
 */
public class DownloadInfo {

    public Context context;

    public long fileSize;

    public String url;

    public int threadId;

    public long startLocation;

    public long endLocation;

    public File tempFile;

    public DownloadInfo(Context context, long fileSize, String url, int threadId, long startLocation, long endLocation, File tempFile) {
        this.context = context;
        this.fileSize = fileSize;
        this.url = url;
        this.threadId = threadId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.tempFile = tempFile;
    }
}
