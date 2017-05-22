package kz.ally.okhttp.callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import kz.ally.okhttp.common.AbsResponse;
import okhttp3.Response;
import kz.ally.okhttp.gson.GsonMapper;

/**
 * 作者：ZhouYou
 * 日期：2017/2/24.
 */
public abstract class GsonCallback<T extends AbsResponse> extends AbsCallback<T> {

    @Override
    public T parseResponse(Response resp) throws IOException {
        String result = resp.body().string();
        Class<T> clazz = transform();
        return GsonMapper.getInstance().getGson().fromJson(result, clazz);
    }

    private Class<T> transform() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] type = parameterizedType.getActualTypeArguments();
        return (Class<T>) type[0];
    }
}
