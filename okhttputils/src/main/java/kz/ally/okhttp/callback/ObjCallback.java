package kz.ally.okhttp.callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import kz.ally.okhttp.common.AbsResponse;
import kz.ally.okhttp.gson.GsonMapper;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public abstract class ObjCallback<T extends AbsResponse> {
    /**
     * UI Thread
     *
     * @param call
     * @param e
     */
    public abstract void onError(Call call, Exception e);

    /**
     * Child Thread
     *
     * @param resp
     * @return
     * @throws IOException
     */
    public T parseResponse(Response resp) throws IOException {
        String result = resp.body().string();
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] type = parameterizedType.getActualTypeArguments();
        Class<T> clazz = (Class<T>) type[0];
        T t = GsonMapper.getInstance().getGson().fromJson(result, clazz);
        return t;
    }

    /**
     * UI Thread
     */
//    public abstract void inProgress(int progress, Call call, Exception e);

    /**
     * UI Thread
     *
     * @param resp
     */
    public abstract void onResponse(T resp);
}
