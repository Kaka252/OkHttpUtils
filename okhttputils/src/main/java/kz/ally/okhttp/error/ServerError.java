package kz.ally.okhttp.error;

import okhttp3.Response;

/**
 * Author: ZhouYou
 * Date: 2017/6/2.
 * 服务器异常
 */
public class ServerError extends AbsError {

    public ServerError(Response response) {
        super(response);
    }

    public ServerError() {
        super();
    }
}
