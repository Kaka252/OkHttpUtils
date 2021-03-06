package com.kanzhun.okhttp.base;

import android.text.TextUtils;
import android.widget.Toast;

import java.util.Set;

import kz.ally.okhttp.callback.ObjCallback;
import kz.ally.okhttp.client.AbsResponse;
import kz.ally.okhttp.error.ErrorReason;
import okhttp3.Headers;

/**
 * Author: ZhouYou
 * Date: 2017/6/3.
 */
public abstract class ApiObjectCallback<T extends AbsResponse> extends ObjCallback<T> {

    @Override
    public void onResponse(T resp) {
        // 处理请求成功的业务
        if (resp.isSuccess()) {
            onComplete(resp);
        }
        // 服务端定义的公共异常
        else if (resp.isServerCommonError()) {
            String msg = resp.message;
            if (resp.isValidationOverTimes()) {
                msg = "每天每个手机号码只允许验证5次";
            }
            if (TextUtils.isEmpty(msg)) {
                msg = "服务器异常：" + resp.code;
            }
            Toast.makeText(App.get().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
        // 服务端定义的业务异常
        else {
            onHandleBusinessError(resp);
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
        // Todo 发送广播Token过期
    }

    private boolean isDecrypt(Headers headers) {
        boolean isDecrypt = false;
        Set<String> keys = headers.names();
        for (String key : keys) {
            if (TextUtils.isEmpty(key)) continue;
            if (key.equals(HEADER_ENCRYPT_KEY)) {
                isDecrypt = TextUtils.equals(headers.get(key), "yes");
            }
        }
        return isDecrypt;
    }

    private static final String HEADER_ENCRYPT_KEY = "content-encrypt";

}
