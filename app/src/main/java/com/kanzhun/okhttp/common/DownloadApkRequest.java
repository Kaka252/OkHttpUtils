package com.kanzhun.okhttp.common;

import java.io.File;

import kz.ally.okhttp.callback.AbsCallback;
import kz.ally.okhttp.client.AbsRequest;
import kz.ally.okhttp.client.RequestMethod;

/**
 * Author: ZhouYou
 * Date: 2017/5/24.
 */
public class DownloadApkRequest extends AbsRequest<File> {

    public DownloadApkRequest(AbsCallback<File> mCallback) {
        this(mCallback, DownloadApkRequest.class.getClass());
    }

    public DownloadApkRequest(AbsCallback<File> mCallback, Object tag) {
        super(mCallback);
        this.tag = tag;
    }

    @Override
    public String getUrl() {
        return "http://1.199.93.153/imtt.dd.qq.com/16891/5FE88135737E977CCCE1A4DAC9FAFFCB.apk"; // 部落冲突
    }

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.GET;
    }

    @Override
    public Object getRequestTag() {
        return tag;
    }

}
