package kz.ally.okhttp;

import java.util.concurrent.TimeUnit;

import kz.ally.okhttp.config.HttpConfig;
import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.method.BatchRequestBuilder;
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
        if (client == null) {
            synchronized (OkHttpSdk.class) {
                if (client == null) {
                    client = initConfig(null);
                }
            }
        }
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

    /**
     * 构建Get请求方法
     *
     * @return
     */
    public GetRequestBuilder get() {
        return new GetRequestBuilder();
    }

    public GetRequestBuilder get(String url) {
        return new GetRequestBuilder(url);
    }

    public GetRequestBuilder get(String url, Params params) {
        return new GetRequestBuilder(url, params);
    }

    /**
     * 构建批量请求方法
     *
     * @return
     */
    public BatchRequestBuilder batch() {
        return new BatchRequestBuilder();
    }

    public BatchRequestBuilder batch(String url) {
        return new BatchRequestBuilder(url);
    }

    public BatchRequestBuilder batch(String url, String batchKey) {
        return new BatchRequestBuilder(url, batchKey);
    }

    /**
     * 构建Post请求方法
     *
     * @return
     */
    public PostRequestBuilder post() {
        return new PostRequestBuilder();
    }

    public PostRequestBuilder post(String url) {
        return new PostRequestBuilder(url);
    }

    public PostRequestBuilder post(String url, Params params) {
        return new PostRequestBuilder(url, params);
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
