package kz.ally.okhttp.callback;

import java.io.IOException;

import kz.ally.okhttp.common.AbsResponse;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：ZhouYou
 * 日期：2017/2/23.
 */
public abstract class AbsCallback<T extends AbsResponse> {
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
