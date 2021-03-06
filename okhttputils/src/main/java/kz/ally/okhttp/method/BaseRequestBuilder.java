package kz.ally.okhttp.method;

import kz.ally.okhttp.ApiRequestCall;
import kz.ally.okhttp.config.RequestHeader;
import kz.ally.okhttp.config.Params;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public abstract class BaseRequestBuilder<T extends BaseRequestBuilder> {

    protected String url;

    protected Object tag;

    protected Params params;

    protected RequestHeader headers;

    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T headers(RequestHeader headers) {
        this.headers = headers;
        return (T) this;
    }

    public RequestHeader getHeaders() {
        return headers;
    }

    public Params getParams() {
        return params;
    }

    public abstract ApiRequestCall build();
}
