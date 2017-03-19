package kz.ally.okhttp;

import android.support.annotation.NonNull;

import java.io.IOException;

import kz.ally.okhttp.callback.AbsCallback;
import kz.ally.okhttp.callback.MainThread;
import kz.ally.okhttp.request.BaseRequest;
import kz.ally.okhttp.response.ApiResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public class ApiRequestCall {

    private Request request;
    private Call call;
    private BaseRequest baseRequest;
    private OkHttpClient client;

    public ApiRequestCall(@NonNull BaseRequest baseRequest) {
        client = OkHttpSdk.getInstance().getClient();
        this.baseRequest = baseRequest;
    }

    private Request setupRequest() {
        return baseRequest.setupRequest();
    }

    private Call newCall() {
        request = setupRequest();
        call = client.newCall(request);
        return call;
    }

    /**
     * 异步调用
     *
     * @param callback
     */
    void async(final AbsCallback callback) {
        Call call = newCall();
        call.enqueue(new ApiResponse(callback));
    }

    /**
     * 请求成功后回调
     *
     * @param callback
     * @param result
     */
    private void successResponseCallback(final AbsCallback callback, final Object result) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(result);
            }
        });
    }

    public Response sync() throws IOException {
        Call call = newCall();
        return call.execute();
    }

    public Request getRequest() {
        return request;
    }

    public Call getCall() {
        return call;
    }

}
