package kz.ally.okhttp.response;

import java.io.IOException;

import kz.ally.okhttp.callback.AbsCallback;
import kz.ally.okhttp.callback.MainThread;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhouyou on 17/3/19.
 */

public class ApiResponse implements Callback {

    private AbsCallback callback;

    public ApiResponse(AbsCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        errorCallback(callback, call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (callback != null) {
            try {
                if (response.isSuccessful()) {
                    Object result = callback.parseResponse(response);
                    parseCallback(callback, result);
                } else {
                    errorCallback(callback, call, null);
                }
            } catch (Exception e) {
                errorCallback(callback, call, e);
            } finally {
                if (response.body() != null) {
                    response.body().close();
                }
            }
        }
    }

    /**
     * 请求失败后回调
     *
     * @param callback
     * @param call
     * @param e
     */
    private void errorCallback(final AbsCallback callback, final Call call, final Exception e) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                if (call.isCanceled()) {
                    callback.onError(call, null);
                } else {
                    callback.onError(call, e);
                }
            }
        });
    }

    /**
     * 请求成功后回调
     *
     * @param callback
     * @param result
     */
    private void parseCallback(final AbsCallback callback, final Object result) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(result);
            }
        });
    }
}
