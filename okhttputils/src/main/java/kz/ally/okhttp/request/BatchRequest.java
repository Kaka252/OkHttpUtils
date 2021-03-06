package kz.ally.okhttp.request;

import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.config.RequestHeader;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 作者：ZhouYou
 * 日期：2017/3/8.
 */
public class BatchRequest extends BaseRequest {

    public BatchRequest(String url, Object tag, Params params, RequestHeader headers) {
        super(url, tag, params, headers);
    }

    @Override
    protected RequestBody createRequestBody() {
        return null;
    }

    @Override
    protected Request createRequest(RequestBody requestBody) {
        return builder.get().build();
    }
}
