package kz.ally.okhttp.common;

/**
 * Author: ZhouYou
 * Date: 2017/5/22.
 */
public interface OnResponseCallback<T extends AbsResponse> {

    void onError();

    void onSuccess(T t);
}
