package com.kanzhun.okhttp.base;

import android.app.Application;
import android.content.Context;

import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import kz.ally.okhttp.OkHttpSdk;
import kz.ally.okhttp.config.HttpConfig;
import kz.ally.okhttp.interceptors.LogInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * 作者：ZhouYou
 * 日期：2017/3/11.
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {


        super.onCreate();
        instance = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(getApplicationContext())
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplicationContext()))
                        .build());
        initOkHttpConfig(this);
        initOkHttpFrescoIntegrationConfig(this);
    }

    public static App get() {
        return instance;
    }

    /**
     * 自定义OKhttp的初始化配置
     *
     * @param context
     */
    private void initOkHttpConfig(Context context) {
        Cache cache = HttpConfig.initCache(context);
        X509TrustManager trustManager = HttpConfig.initInsecureTrustManager();
        SSLSocketFactory sslSocketFactory = HttpConfig.initInsecureSslSocketFactory(trustManager);
        HttpConfig config = new HttpConfig()
                .cache(cache)
                .defaultInterceptor(new LogInterceptor())
                .socketFactory(sslSocketFactory, trustManager)
                .connTimeout(15000)
                .readTimeout(20000)
                .writeTimeout(20000);
        OkHttpSdk.initialize(config);
    }

    private void initOkHttpFrescoIntegrationConfig(Context context) {
        OkHttpClient client = OkHttpSdk.getInstance().getClientFrescoImageLoader();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(context, client)
                .build();
        // Init Fresco
    }
}
