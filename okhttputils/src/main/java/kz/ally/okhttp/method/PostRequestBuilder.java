package kz.ally.okhttp.method;

import kz.ally.okhttp.ApiRequestCall;
import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.interfaces.IParams;
import kz.ally.okhttp.request.PostRequest;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public class PostRequestBuilder extends BaseRequestBuilder<PostRequestBuilder> implements IParams {

    public PostRequestBuilder() {
        if (params == null) {
            params = new Params();
        }
    }

    @Override
    public ApiRequestCall build() {
        return new PostRequest(url, tag, params, headers).createRequestCall();
    }

    @Override
    public PostRequestBuilder addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    @Override
    public PostRequestBuilder addParams(Params params) {
        this.params = params;
        return this;
    }
}
