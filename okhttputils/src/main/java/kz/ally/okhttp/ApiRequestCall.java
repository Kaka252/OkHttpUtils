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

    void async(Callback response) {
        OkHttpClient client = OkHttpSdk.getInstance().getClient();
        request = setupRequest();
        call = client.newCall(request);
        call.enqueue(response);
    }

    void download(Callback response) {
        OkHttpClient client = OkHttpSdk.getInstance().getClientDownload();
        request = setupRequest();
        call = client.newCall(request);
        call.enqueue(response);
    }

    public Request getRequest() {
        return request;
    }

    public Call getCall() {
        return call;
    }

}
