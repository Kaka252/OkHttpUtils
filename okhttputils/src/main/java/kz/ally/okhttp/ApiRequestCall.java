package kz.ally.okhttp;

import android.support.annotation.NonNull;

import kz.ally.okhttp.request.BaseRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public class ApiRequestCall {

    private Request request;
    private Call call;
    private BaseRequest baseRequest;

    public ApiRequestCall(@NonNull BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    private Request setupRequest() {
        return baseRequest.setupRequest();
    }

    /**
     * 普通请求
     * @param callback
     */
    void async(Callback callback) {
        OkHttpClient client = OkHttpSdk.getInstance().getClientDefault();
        request = setupRequest();
        call = client.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 上传/下载
     * @param callback
     */
    void xLoad(Callback callback) {
        OkHttpClient client = OkHttpSdk.getInstance().getClientUploadOrDownload();
        request = setupRequest();
        call = client.newCall(request);
        call.enqueue(callback);
    }

    public Request getRequest() {
        return request;
    }

    public Call getCall() {
        return call;
    }

}
