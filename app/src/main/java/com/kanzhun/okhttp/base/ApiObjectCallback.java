package com.kanzhun.okhttp.base;

import kz.ally.okhttp.callback.ObjCallback;
import kz.ally.okhttp.client.AbsResponse;
import kz.ally.okhttp.error.ErrorReason;

/**
 * Author: ZhouYou
 * Date: 2017/6/3.
 */
public abstract class ApiObjectCallback<T extends AbsResponse> extends ObjCallback<T> {

    @Override
    public void onFailed(ErrorReason reason, Throwable t) {
        onFailed(reason);
    }

    public abstract void onFailed(ErrorReason reason);

    @Override
    public abstract void onResponse(T resp);

    @Override
    public void onLoginError() {
        super.onLoginError();

    }
}
