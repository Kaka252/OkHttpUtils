package kz.ally.okhttp.config;

import android.content.Context;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;

/**
 * 作者：ZhouYou
 * 日期：2017/3/10.
 */
public class HttpConfig {

    public static final String DEFAULT_REQUEST_TAG = "okhttp_defaultRequest";
    /**
     * 默认超时时间
     */
    public static final long DEFAULT_MILLIS_SECOND = 10000;

    public Cache cache;
    public SSLSocketFactory sslSocketFactory;
    public X509TrustManager x509TrustManager;
    public long writeTimeout = DEFAULT_MILLIS_SECOND;
    public long readTimeout = DEFAULT_MILLIS_SECOND;
    public long connectTimeout = DEFAULT_MILLIS_SECOND;

    public HttpConfig cache(Cache cache) {
        this.cache = cache;
        return this;
    }

    public HttpConfig socketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager) {
        this.sslSocketFactory = sslSocketFactory;
        this.x509TrustManager = x509TrustManager;
        return this;
    }

    public HttpConfig writeTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public HttpConfig readTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public HttpConfig connTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public HttpConfig() {
    }

    public static SSLSocketFactory initInsecureSslSocketFactory(TrustManager trustManager) {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            return context.getSocketFactory();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static X509TrustManager initInsecureTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    /**
     * By default, OkHttp does not cache responses that permit caching by including such a HTTP Cache-Control header.
     * Therefore your client may be wasting time and bandwidth by requesting the same resource again and again,
     * as opposed to simply reading a cached copy after the initial response
     *
     * @param context
     * @return
     */
    public static Cache initCache(Context context) {
        File baseDir = context.getCacheDir();
        if (baseDir != null) {
            File cacheDir = new File(baseDir, "HttpResponseCache");
            return new Cache(cacheDir, 10 * 1024 * 1024);
        }
        return null;
    }
}
