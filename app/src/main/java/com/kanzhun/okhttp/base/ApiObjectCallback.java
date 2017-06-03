package com.kanzhun.okhttp.base;

import android.text.TextUtils;
import android.widget.Toast;

import kz.ally.okhttp.callback.ObjCallback;
import kz.ally.okhttp.client.AbsResponse;
import kz.ally.okhttp.error.ErrorReason;

/**
 * Author: ZhouYou
 * Date: 2017/6/3.
 */
public abstract class ApiObjectCallback<T extends AbsResponse> extends ObjCallback<T> {

    @Override
    public void onResponse(T resp) {
        int code = resp.code;
        if (code == 0) {
            onComplete(resp);
        }
        // 服务端定义的公共异常
        else if (code > 0 && code <= 1000) {
            String msg = resp.message;
            if (code == 5) {
                msg = "每天每个手机号码只允许验证5次";
                Toast.makeText(App.get().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(msg)) {
                msg = "服务器异常：" + code;
            }
            Toast.makeText(App.get().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
        // 服务端定义的业务异常
        else if (code > 1000) {
            onHandleBusinessError(resp);
        }
        // 错误
        else {
            Toast.makeText(App.get().getApplicationContext(), "code < 0, 请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 请求成功
     *
     * @param resp
     */
    public abstract void onComplete(T resp);

    /**
     * 业务上的异常处理
     *
     * @param resp
     */
    public void onHandleBusinessError(T resp) {

    }

    /**
     * 请求失败
     *
     * @param reason
     */
    public abstract void onFailed(ErrorReason reason);

    @Override
    public void onFailed(ErrorReason reason, Throwable t) {
        onFailed(reason);
    }

    @Override
    public void onLoginError() {
        super.onLoginError();
        // 发送广播Token过期
    }
}
