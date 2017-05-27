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
        super(mCallback);
    }

    @Override
    public String getUrl() {
        return "http://1.199.93.153/imtt.dd.qq.com/16891/5FE88135737E977CCCE1A4DAC9FAFFCB.apk"; // 高德地图
    }

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.GET;
    }

    @Override
    public Object getTag() {
        if (tag == null) {
            tag = DownloadApkRequest.this.getClass();
        }
        return tag;
    }
}
