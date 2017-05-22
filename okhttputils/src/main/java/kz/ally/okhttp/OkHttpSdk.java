package kz.ally.okhttp;

import java.util.concurrent.TimeUnit;

import kz.ally.okhttp.common.AbsRequest;
import kz.ally.okhttp.common.AbsResponse;
import kz.ally.okhttp.common.RequestMethod;
import kz.ally.okhttp.config.HttpConfig;
import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.method.GetRequestBuilder;
import kz.ally.okhttp.method.PostRequestBuilder;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * 作者：ZhouYou
 * 日期：2017/2/21.
 */
public class OkHttpSdk {

    public static OkHttpSdk getInstance() {
        return OkHttpProxy.SDK;
    }

    private static final class OkHttpProxy {
        private static final OkHttpSdk SDK = new OkHttpSdk();
    }

    private static volatile OkHttpClient client;

    private OkHttpSdk() {
        client = initConfig(null);
    }

    /**
     * 获取OKhttp实例
     *
     * @return
     */
    public OkHttpClient getClient() {
        if (client == null) {
            throw new NullPointerException("OkHttp has not been initialized yet.");
        }
        return client;
    }

    public static OkHttpClient initConfig(HttpConfig config) {
        if (config == null) {
            client = new OkHttpClient.Builder().build();
        } else {
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(config.sslSocketFactory, config.x509TrustManager)
                    .cache(config.cache)
                    .writeTimeout(config.writeTimeout, TimeUnit.MILLISECONDS)
                    .readTimeout(config.readTimeout, TimeUnit.MILLISECONDS)
                    .connectTimeout(config.connectTimeout, TimeUnit.MILLISECONDS)
                    .build();
        }
        return client;
    }

    public <T extends AbsResponse> void executeRequest(AbsRequest<T> request) {
        String url = request.getUrl();
        Params params = request.getParams();
        Object tag = request.getClass();

        if (request.getMethod() == RequestMethod.GET) {
            new GetRequestBuilder()
                    .url(url)
                    .addParams(params)
                    .tag(tag)
                    .build()
                    .async(request.getRawResponseCallback());
        } else if (request.getMethod() == RequestMethod.POST) {
            new PostRequestBuilder()
                    .url(url)
                    .addParams(params)
                    .tag(tag)
                    .build()
                    .async(request.getRawResponseCallback());
        }
    }

    /**
     * 取消默认
     */
    public void cancelDefaultTag() {
        cancelTag(HttpConfig.DEFAULT_REQUEST_TAG);
    }

    /**
     * 取消网络请求
     *
     * @param tag
     */
    public void cancelTag(Object tag) {
        if (tag == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (call == null || call.request() == null) continue;
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (call == null || call.request() == null) continue;
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }
}
