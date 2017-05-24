package kz.ally.okhttp.client;

import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.lang.reflect.Field;

import kz.ally.okhttp.callback.AbsCallback;
import kz.ally.okhttp.callback.MainThread;
import kz.ally.okhttp.config.Params;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Author: ZhouYou
 * Date: 2017/5/22.
 */
public abstract class AbsRequest<T> {

    private static final String TAG = "AbsRequest";

    public AbsRequest(AbsCallback<T> mCallback) {
        this.mCallback = mCallback;
    }

    private AbsCallback<T> mCallback;

    public Object tag;

    public Callback getRawResponseCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                errorCallback(mCallback, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (mCallback != null) {
                    try {
                        if (response.isSuccessful()) {
                            T result = mCallback.parseResponse(response);
                            parseCallback(mCallback, result);
                        } else {
                            errorCallback(mCallback, call, null);
                        }
                    } catch (Exception e) {
                        errorCallback(mCallback, call, e);
                    } finally {
                        if (response.body() != null) {
                            response.body().close();
                        }
                    }
                }
            }
        };
    }

    public abstract String getUrl();

    public abstract RequestMethod getMethod();

    public abstract Object getRequestTag();

    public Params getParams() {
        Params params = new Params();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Expose.class) != null) {
                try {
                    params.put(field.getName(), (field.get(this) + ""));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return params;
    }

    /**
     * 请求失败后回调
     *
     * @param callback
     * @param call
     * @param e
     */
    private void errorCallback(final AbsCallback<T> callback, final Call call, final Exception e) {
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
    private void parseCallback(final AbsCallback<T> callback, final T result) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(result);
            }
        });
    }
}
