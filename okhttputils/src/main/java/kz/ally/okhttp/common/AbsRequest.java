package kz.ally.okhttp.common;

import com.google.gson.annotations.Expose;

import java.lang.reflect.Field;

import kz.ally.okhttp.callback.AbsCallback;
import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.response.ApiResponse;

/**
 * Author: ZhouYou
 * Date: 2017/5/22.
 */
public abstract class AbsRequest<T extends AbsResponse> {

    private static final String TAG = "AbsRequest";

    public AbsRequest(AbsCallback<T> mCallback) {
        this.mCallback = mCallback;
    }

    private AbsCallback<T> mCallback;

    public ApiResponse getRawResponseCallback() {
        return new ApiResponse(mCallback);
    }

    public abstract String getUrl();

    public abstract RequestMethod getMethod();

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

}
