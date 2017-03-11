package kz.ally.okhttp.method;

import java.io.File;
import java.util.Map;

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
        this("");
    }

    public PostRequestBuilder(String url) {
        this(url, null);
    }

    public PostRequestBuilder(String url, Params ps) {
        this.url = url;
        params = ps;
        if (params == null) {
            params = new Params();
        }
    }

    @Override
    public ApiRequestCall build() {
        return new PostRequest(url, tag, params, headers).createRequestCall();
    }

    @Override
    public PostRequestBuilder addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    @Override
    public PostRequestBuilder addParams(Map<String, String> p) {
        params.put(p);
        return this;
    }

    @Override
    public PostRequestBuilder addParams(Params params) {
        if (params == null) {
            throw new NullPointerException("Params must not be null.");
        }
        this.params = params;
        return this;
    }

    public PostRequestBuilder addFile(String key, File f) {
        if (f == null) {
            throw new NullPointerException("File must not be null.");
        }
        params.putFile(key, f);
        return this;
    }

    public PostRequestBuilder addFiles(Map<String, File> fs) {
        if (fs == null) {
            throw new NullPointerException("File must not be null.");
        }
        params.putFiles(fs);
        return this;
    }
}
