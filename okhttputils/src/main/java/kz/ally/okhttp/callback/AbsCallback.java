package kz.ally.okhttp.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Author: ZhouYou
 * Date: 2017/5/24.
 */
public abstract class AbsCallback<T> {

    /**
     * UI Thread
     *
     * @param call
     * @param e
     */
    public abstract void onError(Call call, Exception e);

    /**
     * Child Thread
     *
     * @param resp
     * @return
     * @throws IOException
     */
    public abstract T parseResponse(Response resp) throws IOException;

    /**
     * UI Thread
     */
//    public abstract void inProgress(int progress, Call call, Exception e);

    /**
     * UI Thread
     *
     * @param resp
     */
    public abstract void onResponse(T resp);
}
