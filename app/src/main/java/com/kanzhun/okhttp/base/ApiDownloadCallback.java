package com.kanzhun.okhttp.base;

import java.io.File;

import kz.ally.okhttp.callback.FileCallback;
import kz.ally.okhttp.error.ErrorReason;

/**
 * Author: ZhouYou
 * Date: 2017/6/3.
 */
public abstract class ApiDownloadCallback extends FileCallback {

    public ApiDownloadCallback(String dir, String fileName) {
        super(dir, fileName);
    }

    public abstract void onFailed(ErrorReason reason);

    @Override
    public void onFailed(ErrorReason reason, Throwable t) {
        onFailed(reason);
    }

    @Override
    public abstract void onResponse(File resp);

    @Override
    protected abstract void inProgress(double progress, long total);
}
