package kz.ally.okhttp.callback;

import java.io.IOException;

import kz.ally.okhttp.error.AbsError;
import kz.ally.okhttp.error.ErrorReason;
import okhttp3.Response;

/**
 * Author: ZhouYou
 * Date: 2017/5/24.
 */
public abstract class AbsCallback<T> {

    /**
     * UI Thread
     */
    public void onStart() {

    }

    /**
     * UI Thread
     *
     * @param reason
     */
    public abstract void onFailed(ErrorReason reason);

    /**
     * Child Thread
     *
     * @param resp
     * @return
     * @throws IOException
     */
    public abstract T parseResponse(Response resp) throws IOException, AbsError;

    /**
     * UI Thread
     *
     * @param resp
     */
    public abstract void onResponse(T resp);

    /**
     * 登录异常
     */
    public void onLoginError() {

    }
}
