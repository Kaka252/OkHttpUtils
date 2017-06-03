package kz.ally.okhttp;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;

import kz.ally.okhttp.callback.AbsCallback;
import kz.ally.okhttp.callback.MainThread;
import kz.ally.okhttp.error.AbsError;
import kz.ally.okhttp.error.DownloadError;
import kz.ally.okhttp.error.ErrorReason;
import kz.ally.okhttp.error.LoginError;
import kz.ally.okhttp.error.NetworkError;
import kz.ally.okhttp.error.ParseError;
import kz.ally.okhttp.error.ServerError;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Author: ZhouYou
 * Date: 2017/6/2.
 */
public class ApiResponseCallback<T> implements Callback {

    private AbsCallback<T> mCallback;

    public ApiResponseCallback(AbsCallback<T> mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (mCallback != null) {
            errorCallback(mCallback, new NetworkError(e));
        }
        if (!call.isCanceled()) {
            call.cancel();
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (mCallback != null) {
            try {
                if (response.isSuccessful()) {
                    T result = mCallback.parseResponse(response);
                    parseCallback(mCallback, result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AbsError error) {
                errorCallback(mCallback, error);
            } finally {
                if (response.body() != null) {
                    response.body().close();
                }
            }
        }
    }

    /**
     * 请求失败后回调
     *
     * @param callback
     * @param error
     */
    private void errorCallback(final AbsCallback<T> callback, final AbsError error) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                ErrorReason reason;
                Throwable t = null;
                if (error == null) {
                    reason = ErrorReason.OTHER;
                } else if (error instanceof NetworkError
                        || error instanceof ServerError) {
                    reason = ErrorReason.NET_ERROR;
                    t = error.getCause();
                    if (t == null) {
                        String msg = ErrorReason.NET_ERROR.getReason();
                        Response response = error.response;
                        if (response != null) {
                            msg += " | error code: " + response.code();
                        }
                        t = new ConnectException(msg);
                    }
                } else if (error instanceof DownloadError) {
                    reason = ErrorReason.DOWNLOAD_ERROR;
                    t = error.getCause();
                    if (t == null) {
                        String msg = ErrorReason.OTHER.getReason();
                        Response response = error.response;
                        if (response != null) {
                            msg += " | error code: " + response.code();
                        }
                        t = new JSONException(msg);
                    }
                } else if (error instanceof ParseError) {
                    reason = ErrorReason.JSON_ERROR;
                    t = error.getCause();
                    if (t == null) {
                        String msg = ErrorReason.JSON_ERROR.getReason();
                        Response response = error.response;
                        if (response != null) {
                            msg += "| error code: " + response.code();
                        }
                        t = new Exception(msg);
                    }
                } else if (error instanceof LoginError) {
                    errorLogin(callback);
                    return;
                } else {
                    reason = ErrorReason.OTHER;
                    t = error.getCause();
                    if (t == null) {
                        String msg = ErrorReason.OTHER.getReason();
                        Response response = error.response;
                        if (response != null) {
                            msg += " | error code: " + response.code();
                        }
                        t = new Exception(msg);
                    }
                }
                callback.onFailed(reason, t);
            }
        });
    }

    /**
     * 登录错误
     * @param callback
     */
    private void errorLogin(final AbsCallback<T> callback) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                callback.onLoginError();
            }
        });
    }

    /**
     * 请求成功后回调
     *
     * @param callback
     * @param result
     */
    private void parseCallback(final AbsCallback<T> callback, final T result) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(result);
            }
        });
    }
}
