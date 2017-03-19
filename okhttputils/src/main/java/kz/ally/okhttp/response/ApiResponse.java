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
        failResponseCallback(callback, call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            if (response.isSuccessful()) {
                Object result = callback.parseResponse(response);
                successResponseCallback(callback, result);
            } else {
                failResponseCallback(callback, call, null);
            }
        } catch (Exception e) {
            failResponseCallback(callback, call, e);
        } finally {
            if (response.body() != null) {
                response.body().close();
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
    private void failResponseCallback(final AbsCallback callback, final Call call, final Exception e) {
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
    private void successResponseCallback(final AbsCallback callback, final Object result) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(result);
            }
        });
    }
}
