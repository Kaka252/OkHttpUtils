package com.kanzhun.okhttp.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import kz.ally.okhttp.OkHttpSdk;
import kz.ally.okhttp.config.HttpConfig;
import okhttp3.Cache;

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
                .socketFactory(sslSocketFactory, trustManager)
                .connTimeout(15000)
                .readTimeout(20000)
                .writeTimeout(20000);
        OkHttpSdk.initConfig(config);
    }
}
