package kz.ally.okhttp.method;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.Set;

import kz.ally.okhttp.ApiRequestCall;
import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.interfaces.IParams;
import kz.ally.okhttp.request.GetRequest;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public class GetRequestBuilder extends BaseRequestBuilder<GetRequestBuilder> implements IParams {

    private static final String TAG = "GetRequestBuilder";

    public GetRequestBuilder() {
        if (params == null) {
            params = new Params();
        }
    }

    @Override
    public GetRequestBuilder addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    @Override
    public GetRequestBuilder addParams(Params params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                if (TextUtils.isEmpty(key)) continue;
                Object o = params.get(key);
                addParam(key, o);
            }
        }
        return this;
    }

    @Override
    public ApiRequestCall build() {
        if (!params.isEmpty()) {
            url = join(url);
        }
        return new GetRequest(url, tag, params, headers).createRequestCall();
    }

    /**
     * 拼装url和params
     *
     * @param url
     * @return
     */
    String join(String url) {
        if (TextUtils.isEmpty(url) || params == null || params.isEmpty()) return url;
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            if (TextUtils.isEmpty(key)) continue;
            String value = castString(params.get(key));
            if (TextUtils.isEmpty(value)) continue;
            builder.appendQueryParameter(key, value);
        }
        Log.d(TAG, builder.build().toString());
        return builder.build().toString();
    }

    public String castString(Object o) {
        String value = "";
        if (o instanceof Long) {
            value = String.valueOf(o);
        } else if (o instanceof Float) {
            value = String.valueOf(o);
        } else if (o instanceof Short) {
            value = String.valueOf(o);
        } else if (o instanceof Integer) {
            value = String.valueOf(o);
        } else if (o instanceof Boolean) {
            value = String.valueOf(o);
        } else if (o instanceof String) {
            value = (String) o;
        }
        return value;
    }
}
