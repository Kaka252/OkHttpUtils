package kz.ally.okhttp.method;

import kz.ally.okhttp.ApiRequestCall;
import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.interfaces.IParams;
import kz.ally.okhttp.request.GetRequest;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public class GetRequestBuilder extends BaseRequestBuilder<GetRequestBuilder> implements IParams {

    public GetRequestBuilder() {
        if (params == null) {
            params = new Params();
        }
    }

    @Override
    public GetRequestBuilder addParam(String key, Object value) {
        if (value instanceof String) {
            params.put(key, (String) value);
        }
        return this;
    }

    @Override
    public GetRequestBuilder addParams(Params params) {
        if (params == null) {
            throw new NullPointerException("Params must not be null.");
        }
        this.params = params;
        return this;
    }

    @Override
    public ApiRequestCall build() {
        if (!params.isEmpty()) {
            url = params.join(url);
        }
        return new GetRequest(url, tag, params, headers).createRequestCall();
    }
}
