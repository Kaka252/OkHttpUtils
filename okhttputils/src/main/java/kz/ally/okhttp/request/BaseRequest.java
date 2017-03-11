package kz.ally.okhttp.request;

import android.text.TextUtils;

import java.util.Map;

import kz.ally.okhttp.ApiRequestCall;
import kz.ally.okhttp.config.Params;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public abstract class BaseRequest {

    protected String url;
    protected Object tag;
    protected Params params;
    protected Map<String, String> headers;
    protected Request.Builder builder;

    BaseRequest(String url, Object tag, Params params, Map<String, String> headers) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        initRequestBuilder();
    }

    private void initRequestBuilder() {
        builder = new Request.Builder()
                .url(url)
                .tag(tag)
                .headers(getHeaders())
                .cacheControl(getCacheControl());
    }

    private Headers getHeaders() {
        Headers.Builder builder = new Headers.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                if (TextUtils.isEmpty(key)) continue;
                builder.add(key, headers.get(key));
            }
        }
        return builder.build();
    }

    private CacheControl getCacheControl() {
        CacheControl.Builder builder = new CacheControl.Builder();
        return builder.build();
    }

    public ApiRequestCall createRequestCall() {
        return new ApiRequestCall(this);
    }

    /**
     * 建立最终配置好参数之后的网络请求
     * @return
     */
    public Request setupRequest() {
        return createRequest(createRequestBody());
    }

    /**
     * 创建请求体
     *
     * @return
     */
    protected abstract RequestBody createRequestBody();

    /**
     * 创建请求
     *
     * @param requestBody
     * @return
     */
    protected abstract Request createRequest(RequestBody requestBody);

}
