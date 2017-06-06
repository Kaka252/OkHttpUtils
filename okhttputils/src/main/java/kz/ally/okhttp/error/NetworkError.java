package kz.ally.okhttp.error;

import okhttp3.Response;

/**
 * Author: ZhouYou
 * Date: 2017/6/2.
 * 网络服务异常
 */
public class NetworkError extends AbsError {

    public NetworkError() {
        super();
    }

    public NetworkError(Response response) {
        super(response);
    }

    public NetworkError(Throwable cause) {
        super(cause);
    }
}
