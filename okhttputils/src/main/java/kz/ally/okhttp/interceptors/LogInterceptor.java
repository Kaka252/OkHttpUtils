package kz.ally.okhttp.interceptors;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Author: ZhouYou
 * Date: 2017/5/23.
 */
public class LogInterceptor implements Interceptor {

    private static final String TAG = "LogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long timeStart = System.nanoTime();
        Response response = chain.proceed(request);
        long timeEnd = System.nanoTime();
        double time = (timeEnd - timeStart) / 1e6d;

        String methodType = "";
        if (request.method().equals("GET")) {    //判断Method类型
            methodType = "GET";
        } else if (request.method().equals("POST")) {
            methodType = "POST";
        }

        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);

        Buffer buffer = source.buffer();
        double dataCount = (buffer.size() / 1e3d);

        StringBuffer sb = new StringBuffer();
        sb.append("\n--------------------------------------------------------");
        sb.append("\n请求时间：").append(time);

        sb.append("\nUrl = ").append(request.url());
        sb.append("\nMethod = ").append(methodType);
        sb.append("\nRequest Headers = ").append(request.headers());
        sb.append("\nResponse Code = ").append(response.code());
        sb.append("\nData = ").append(dataCount).append("K");
        sb.append("\n--------------------------------------------------------");
        Log.d(TAG, sb.toString());
        return response;
    }
}
