package com.kanzhun.okhttp.common;

import com.google.gson.annotations.Expose;

import kz.ally.okhttp.callback.ObjCallback;
import kz.ally.okhttp.client.AbsRequest;
import kz.ally.okhttp.client.RequestMethod;

/**
 * Author: ZhouYou
 * Date: 2017/5/22.
 */
public class GetMusicListRequest extends AbsRequest {

    @Expose
    public String q;

    @Expose
    public int start;

    @Expose
    public int count;

    public GetMusicListRequest(ObjCallback mCallback) {
        super(mCallback);
    }

    @Override
    public String getUrl() {
        return "https://api.douban.com/v2/music/search";
    }

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.GET;
    }
}
