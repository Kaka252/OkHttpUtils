package kz.ally.okhttp;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import kz.ally.okhttp.chuck.ChuckInterceptor;
import kz.ally.okhttp.client.AbsRequest;
import kz.ally.okhttp.client.AbsResponse;
import kz.ally.okhttp.client.RequestMethod;
import kz.ally.okhttp.config.HttpConfig;
import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.method.GetRequestBuilder;
import kz.ally.okhttp.method.PostRequestBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

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

    private static volatile OkHttpClient clientDefault; // 普通请求
    private static volatile OkHttpClient clientUploadOrDownload; // 用来做下载
    private static volatile OkHttpClient clientFrescoImageLoader; // fresco图片

    private OkHttpSdk() {
    }

    public static void initialize(Context context) {
        initialize(context, null);
    }

    public static void initialize(Context context, HttpConfig config) {
        clientDefault = initConfig(context, config);
        clientUploadOrDownload = initConfig(context, config);
        clientFrescoImageLoader = initConfig(context, config);
    }

    /**
     * 获取OKhttp实例
     *
     * @return
     */
    public OkHttpClient getClientDefault() {
        if (clientDefault == null) {
            throw new NullPointerException("OkHttp has not been initialized yet.");
        }
        return clientDefault;
    }

    public OkHttpClient getClientUploadOrDownload() {
        if (clientUploadOrDownload == null) {
            throw new NullPointerException("OkHttp has not been initialized yet.");
        }
        return clientUploadOrDownload;
    }

    public OkHttpClient getClientFrescoImageLoader() {
        if (clientFrescoImageLoader == null) {
            throw new NullPointerException("OkHttp has not been initialized yet.");
        }
        return clientFrescoImageLoader;
    }

    private static OkHttpClient initConfig(Context context, HttpConfig config) {
        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (config == null) {
            client = builder.build();
        } else {
            builder.sslSocketFactory(config.sslSocketFactory, config.x509TrustManager);
            builder.writeTimeout(config.writeTimeout, TimeUnit.MILLISECONDS);
            builder.readTimeout(config.readTimeout, TimeUnit.MILLISECONDS);
            builder.connectTimeout(config.connectTimeout, TimeUnit.MILLISECONDS);
            if (config.cache != null) {
                builder.cache(config.cache);
            }
            builder.addInterceptor(new ChuckInterceptor(context));
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            client = builder.build();
        }
        return client;
    }

    /**
     * 执行Get/Post请求
     *
     * @param request
     * @param <T>
     */
    public <T extends AbsResponse> void executeRequest(AbsRequest<T> request) {
        String url = request.getUrl();
        Params params = request.getParams();
        if (request.getMethod() == RequestMethod.GET) {
            new GetRequestBuilder()
                    .url(url)
                    .addParams(params)
                    .tag(request.getTag())
                    .build()
                    .async(request.getRawResponseCallback());
        } else if (request.getMethod() == RequestMethod.POST) {
            new PostRequestBuilder()
                    .url(url)
                    .addParams(params)
                    .tag(request.getTag())
                    .build()
                    .async(request.getRawResponseCallback());
        }
    }

    /**
     * 执行下载
     *
     * @param request
     */
    public void executeDownload(AbsRequest<File> request) {
        String url = request.getUrl();
        Params params = request.getParams();
        new GetRequestBuilder()
                .url(url)
                .addParams(params)
                .tag(request.getTag())
                .build()
                .xLoad(request.getRawResponseCallback());
    }

    /**
     * 执行上传操作
     *
     * @param request
     * @param <T>
     */
    public <T extends AbsResponse> void executeUpload(AbsRequest<T> request) {
        String url = request.getUrl();
        Params params = request.getParams();
        new PostRequestBuilder()
                .url(url)
                .addParams(params)
                .tag(request.getTag())
                .build()
                .xLoad(request.getRawResponseCallback());
    }

    public <T extends AbsResponse> void executeBatch(AbsRequest<T> request) {

    }
}