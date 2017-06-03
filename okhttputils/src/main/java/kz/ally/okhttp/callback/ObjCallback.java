package kz.ally.okhttp.callback;

import android.text.TextUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import kz.ally.okhttp.client.AbsResponse;
import kz.ally.okhttp.error.AbsError;
import kz.ally.okhttp.error.LoginError;
import kz.ally.okhttp.error.ParseError;
import kz.ally.okhttp.gson.GsonMapper;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public abstract class ObjCallback<T extends AbsResponse> extends AbsCallback<T> {

    private static final String HEADER_ENCRYPT_KEY = "content-encrypt";

    /**
     * Child Thread
     *
     * @param resp
     * @return
     * @throws IOException
     */
    public T parseResponse(Response resp) throws IOException, AbsError {
        String result = resp.body().string();
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] type = parameterizedType.getActualTypeArguments();
        Class<T> clazz = (Class<T>) type[0];
        T t = GsonMapper.getInstance().getGson().fromJson(result, clazz);
        if (t == null) throw new ParseError();
        if (!t.isSuccess()) {
            if (t.isTokenExpired()) {
                throw new LoginError();
            }
        }
        return t;
    }

    private boolean isDecrypt(Headers headers) {
        boolean isDecrypt = false;
        Set<String> keys = headers.names();
        for (String key : keys) {
            if (TextUtils.isEmpty(key)) continue;
            if (key.equals(HEADER_ENCRYPT_KEY)) {
                isDecrypt = TextUtils.equals(headers.get(key), "yes");
            }
        }
        return isDecrypt;
    }
}
